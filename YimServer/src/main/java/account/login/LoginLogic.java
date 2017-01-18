package account.login;

import bean.OfflineMsgBean;
import bean.UserBean;
import connection.ConnPool;
import connection.TokenFactory;
import connection.TokenPool;
import dao.FriendReqDao;
import dao.OfflineMsgDao;
import dao.UserDao;
import friends.FriendAddReqPacket;
import friends.FriendReplyReqPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.ReferenceCountUtil;
import message.person.PersonMsgReqPacket;
import org.apache.log4j.Logger;
import transport.HeartbeatHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 登录逻辑
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class LoginLogic {
    private static final Logger LOGGER = Logger.getLogger(LoginLogic.class);

    private LoginReqPacket loginReqPacket;
    private Channel channel;
    private String username;

    public LoginLogic(LoginReqPacket loginReqPacket, Channel channel) {
        this.loginReqPacket = loginReqPacket;
        this.channel = channel;
    }

    /**
     * 登录信息验证
     */
    public void deal() {
        username = loginReqPacket.getUsername();
        String password = loginReqPacket.getPassword();
        UserDao userDao = null;
        try {
            userDao = new UserDao();
            List<UserBean> users = userDao.queryByUsername(username);
            if (users.size() == 1) {
                if (password.equals(users.get(0).getPassword())) {
                    // 成功
                    success();
                } else {
                    // 失败，密码错误
                    defeat("密码错误");
                }
            } else {
                // 失败，用户名错误
                defeat("用户名错误");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("MySQL连接异常", e);
        } catch (SQLException e) {
            LOGGER.warn("MySQL连接异常", e);
        } finally {
            if (userDao != null) {
                userDao.close();
            }
            ReferenceCountUtil.release(loginReqPacket);
        }
    }

    /**
     * 信息验证成功
     */
    private void success() {
        // 生成token
        final TokenFactory factory = new TokenFactory();
        final Long token = factory.generate();
        // 维护连接
        boolean b1 = ConnPool.add(username, channel);
        // 维护token
        boolean b2 = TokenPool.add(token);
        if (b1 && b2) {
            // 发送响应数据包
            LoginRespPacket loginRespPacket = new LoginRespPacket(username, true, token, null);
            ChannelFuture future = channel.writeAndFlush(loginRespPacket);
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        LOGGER.info(username + " 登录成功");
                        // 开启心跳检测
                        LOGGER.info(username + " 开启心跳检测");
                        channel.pipeline().addAfter("IdleStateHandler",
                                "HeartbeatHandler", new HeartbeatHandler(channel));
                        // 发送离线消息
                        sendOfflineMsg();
                        // 发送离线通知
                        sendOfflineNotice();
                    } else {
                        LOGGER.warn(username + " 登录成功响应包发送失败");
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
     * 信息验证失败
     *
     * @param hint
     */
    private void defeat(String hint) {
        // 发送响应数据包
        LoginRespPacket loginRespPacket = new LoginRespPacket(username, false, null, hint);
        ChannelFuture future = channel.writeAndFlush(loginRespPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    LOGGER.info(username + " 登录失败");
                    channel.close();
                } else {
                    LOGGER.warn(username + " 登录失败响应包发送失败");
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
                int row = new OfflineMsgDao().removeMsg(username);
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
