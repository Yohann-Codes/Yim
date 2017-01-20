package account.relogin;

import bean.OfflineMsgBean;
import bean.OfflineMsgGroupBean;
import connection.ConnPool;
import connection.TokenPool;
import dao.FriendReqDao;
import dao.OfflineMsgDao;
import dao.OfflineMsgGroupDao;
import friends.FriendAddReqPacket;
import friends.FriendReplyReqPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import message.group.GroupMsgReqPacket;
import message.person.PersonMsgReqPacket;
import org.apache.log4j.Logger;
import transport.HeartbeatHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 客户端重连逻辑
 * <p>
 * Created by yohann on 2017/1/20.
 */
public class ReLoginLogic {
    private static final Logger LOGGER = Logger.getLogger(ReLoginLogic.class);

    private String username;
    private Long token;
    private Channel channel;

    public ReLoginLogic(ReLoginReqPacket reLoginReqPacket, Channel channel) {
        username = reLoginReqPacket.getUsername();
        token = reLoginReqPacket.getToken();
        this.channel = channel;
    }

    public void deal() {
        // 验证token
        if (TokenPool.query(token)) {
            success();
        } else {
            // token验证失败
            defeat("token验证失败");
            LOGGER.info("token验证失败，拒绝重连");
        }
    }

    /**
     * token验证成功
     */
    private void success() {
        // 维护连接
        boolean b = ConnPool.add(username, channel);
        if (b) {
            // 发送响应数据包
            ReLoginRespPacket reLoginRespPacket = new ReLoginRespPacket(true, null);
            ChannelFuture future = channel.writeAndFlush(reLoginRespPacket);
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        LOGGER.info(username + " 重连成功");
                        // 开启心跳检测
                        LOGGER.info(username + " 开启心跳检测");
                        channel.pipeline().addAfter("IdleStateHandler",
                                "HeartbeatHandler", new HeartbeatHandler(channel));
                        // 发送离线消息
                        sendOfflineMsg();
                        sendOfflineGroupMsg();
                        // 发送离线通知
                        sendOfflineNotice();
                    } else {
                        LOGGER.warn(username + " 重连成功响应包发送失败");
                        ConnPool.remove(username);
                        TokenPool.remove(token);
                    }
                }
            });
        } else {
            defeat("服务器内部错误");
        }
    }

    /**
     * token验证失败
     *
     * @param hint
     */
    private void defeat(String hint) {
        // 发送响应数据包
        ReLoginRespPacket reLoginRespPacket = new ReLoginRespPacket(false, hint);
        ChannelFuture future = channel.writeAndFlush(reLoginRespPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    LOGGER.info(username + " 重连失败");
                    channel.close();
                } else {
                    LOGGER.warn(username + " 重连失败响应包发送失败");
                }
            }
        });
    }

    /**
     * 发送离线消息
     */
    private void sendOfflineMsg() {
        OfflineMsgDao offlineMsgDao = null;
        try {
            offlineMsgDao = new OfflineMsgDao();
            // 查询消息
            List<OfflineMsgBean> offlineMsgs = offlineMsgDao.queryMsg(username);
            if (offlineMsgs.size() != 0) {
                // 一个一个发送
                for (int i = 0; i < offlineMsgs.size(); i++) {
                    OfflineMsgBean offlineMsgBean = offlineMsgs.get(i);
                    PersonMsgReqPacket personMsgReqPacket =
                            new PersonMsgReqPacket(
                                    offlineMsgBean.getSender(),
                                    offlineMsgBean.getReceiver(),
                                    offlineMsgBean.getMessage(),
                                    offlineMsgBean.getTime());
                    channel.writeAndFlush(personMsgReqPacket);
                    LOGGER.info("离线消息 " + offlineMsgBean.getSender()
                            + "-->" + offlineMsgBean.getReceiver() + " 发送成功");
                }
                // 删除离线消息
                int row = offlineMsgDao.removeMsg(username);
                if (row == offlineMsgs.size()) {
                    LOGGER.info("删除离线消息 成功");
                } else {
                    LOGGER.warn("删除离线消息 失败");
                }
            } else {
                return;
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("MySQL连接异常", e);
        } catch (SQLException e) {
            LOGGER.warn("MySQL连接异常", e);
        } finally {
            if (offlineMsgDao != null) {
                offlineMsgDao.close();
            }
        }
    }

    /**
     * 发送讨论组离线消息
     */
    private void sendOfflineGroupMsg() {
        OfflineMsgGroupDao offlineMsgGroupDao = null;
        try {
            offlineMsgGroupDao = new OfflineMsgGroupDao();
            // 查询消息
            List<OfflineMsgGroupBean> offlineMsgs = offlineMsgGroupDao.queryMsg(username);
            if (offlineMsgs.size() != 0) {
                // 一个一个发送
                for (int i = 0; i < offlineMsgs.size(); i++) {
                    OfflineMsgGroupBean offlineMsgGroupBean = offlineMsgs.get(i);
                    GroupMsgReqPacket groupMsgReqPacket =
                            new GroupMsgReqPacket(
                                    offlineMsgGroupBean.getSender(),
                                    offlineMsgGroupBean.getGroup(),
                                    offlineMsgGroupBean.getMessage(),
                                    offlineMsgGroupBean.getTime());
                    channel.writeAndFlush(groupMsgReqPacket);
                    LOGGER.info("讨论组离线消息 " + offlineMsgGroupBean.getSender()
                            + "-->" + offlineMsgGroupBean.getGroup() + "-->" + username + " 发送成功");
                }
                // 删除离线消息
                int row = offlineMsgGroupDao.removeMsg(username);
                if (row == offlineMsgs.size()) {
                    LOGGER.info("删除讨论组离线消息 成功");
                } else {
                    LOGGER.warn("删除讨论组离线消息 失败");
                }
            } else {
                return;
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("MySQL连接异常", e);
        } catch (SQLException e) {
            LOGGER.warn("MySQL连接异常", e);
        } finally {
            if (offlineMsgGroupDao != null) {
                offlineMsgGroupDao.close();
            }
        }
    }

    /**
     * 发送离线通知
     */
    private void sendOfflineNotice() {
        FriendReqDao friendReqDao = null;
        try {
            friendReqDao = new FriendReqDao();
            // 查询自己未处理的消息
            List<FriendAddReqPacket> friendAddReqs = friendReqDao.queryUntreatReq(username);
            if (friendAddReqs.size() != 0) {
                for (int i = 0; i < friendAddReqs.size(); i++) {
                    FriendAddReqPacket friendAddReqPacket = friendAddReqs.get(i);
                    channel.writeAndFlush(friendAddReqPacket);
                    LOGGER.info("添加好友请求 " + friendAddReqPacket.getUsername()
                            + "-->" + friendAddReqPacket.getResponser() + " 发送成功");
                }
            }
            // 查询别人已处理的请求
            List<FriendReplyReqPacket> friendReplyReqs = friendReqDao.queryTreatedReq(username);
            if (friendReplyReqs.size() != 0) {
                for (int i = 0; i < friendReplyReqs.size(); i++) {
                    FriendReplyReqPacket friendReplyReqPacket = friendReplyReqs.get(i);
                    channel.writeAndFlush(friendReplyReqPacket);
                    LOGGER.info("添加好友请求回复 " + friendReplyReqPacket.getRequester()
                            + "-->" + friendReplyReqPacket.getUsername() + " 发送成功");
                    // 删除
                    friendReqDao.removeFriendReq(friendReplyReqPacket.getRequester(),
                            friendReplyReqPacket.getUsername());
                }
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("MySQL连接异常", e);
        } catch (SQLException e) {
            LOGGER.warn("MySQL连接异常", e);
        } finally {
            if (friendReqDao != null) {
                friendReqDao.close();
            }
        }
    }
}
