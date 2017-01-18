package friends;

import bean.UserBean;
import connection.ConnPool;
import dao.FriendDao;
import dao.FriendReqDao;
import dao.UserDao;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * 添加好友回复逻辑
 * <p>
 * Created by yohann on 2017/1/18.
 */
public class FriendReplyReqLogic {
    private static final Logger LOGGER = Logger.getLogger(FriendReplyReqLogic.class);

    private FriendReplyReqPacket friendReplyReqPacket;
    private Channel channel;
    private String requester;

    private UserDao userDao;
    private FriendDao friendDao;
    private FriendReqDao friendReqDao;
    private String username;

    public FriendReplyReqLogic(FriendReplyReqPacket friendReplyReqPacket, Channel channel) {
        this.friendReplyReqPacket = friendReplyReqPacket;
        this.channel = channel;
    }

    public void deal() {
        username = friendReplyReqPacket.getUsername();
        boolean isAgree = friendReplyReqPacket.isAgree();
        requester = friendReplyReqPacket.getRequester();

        try {
            userDao = new UserDao();
            friendDao = new FriendDao();
            friendReqDao = new FriendReqDao();

            // 查询是否存在该用户
            List<UserBean> users = userDao.queryByUsername(requester);
            if (users.size() == 1) {
                if (isAgree) {
                    // 同意添加好友
                    friendReqDao.updateFriendReq(requester, username, "yes");
                    // 添加到好友列表(双方)
                    String c1 = friendDao.queryNoFriColumn(requester);
                    if (c1 != null) {
                        friendDao.insertFriend(requester, username, c1);
                    }
                    String c2 = friendDao.queryNoFriColumn(username);
                    if (c2 != null) {
                        friendDao.insertFriend(username, requester, c2);
                    }
                    LOGGER.info("好友添加成功");
                    agree();
                } else {
                    // 拒绝添加好友
                    friendReqDao.updateFriendReq(requester, username, "no");
                    LOGGER.info("拒绝添加好友");
                    disagree();
                }
            } else {
                // 不存在该用户
                defeat("不存在该用户");
                LOGGER.info("回复添加好友请求 " + username + " --> " + requester + " 发送失败（不存在该用户）");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("MySQL连接异常", e);
        } catch (SQLException e) {
            LOGGER.warn("MySQL连接异常", e);
        } finally {
            if (userDao != null) {
                userDao.close();
            }
            if (friendDao != null) {
                friendDao.close();
            }
            if (friendReqDao != null) {
                friendReqDao.close();
            }
        }
    }

    /**
     * 同意添加好友
     */
    private void agree() {
        // 查询requester是否在线
        Channel recChannel = ConnPool.query(requester);
        if (recChannel != null) {
            // 发送
            ChannelFuture future = recChannel.writeAndFlush(friendReplyReqPacket);
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        // 转发成功
                        LOGGER.info("回复添加好友请求 " + username + " --> " + requester + " 转发成功");
                        success();
                    } else {
                        LOGGER.warn("回复添加好友请求 " + username + " --> " + requester + " 转发失败");
                    }
                }
            });
            // 删除请求信息
            int r = friendReqDao.removeFriendReq(requester, username);
            if (r != 0) {
                LOGGER.info("请求信息删除成功");
            }
        } else {
            LOGGER.info("回复添加好友请求 " + username + " --> " + requester + " 请求信息修改成功");
            success();
        }
    }

    /**
     * 拒绝添加好友
     */
    private void disagree() {
        // 查询requester是否在线
        Channel recChannel = ConnPool.query(requester);
        if (recChannel != null) {
            // 发送
            ChannelFuture future = recChannel.writeAndFlush(friendReplyReqPacket);
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        // 转发成功
                        LOGGER.info("回复添加好友请求 " + username + " --> " + requester + " 转发成功");
                        success();
                    } else {
                        LOGGER.warn("回复添加好友请求 " + username + " --> " + requester + " 转发失败");
                    }
                }
            });
            // 删除请求信息
            int r = friendReqDao.removeFriendReq(requester, username);
            if (r != 0) {
                LOGGER.info("请求信息删除成功");
            }
        } else {
            LOGGER.info("回复添加好友请求 " + username + " --> " + requester + " 请求信息修改成功");
            success();
        }
    }

    private void success() {
        FriendReplyRespPacket friendReplyRespPacket = new FriendReplyRespPacket(true, null);
        repsonse(friendReplyRespPacket);
    }

    private void defeat(String hint) {
        FriendReplyRespPacket friendReplyRespPacket = new FriendReplyRespPacket(false, hint);
        repsonse(friendReplyRespPacket);
    }

    /**
     * 发送响应
     *
     * @param friendReplyRespPacket
     */
    private void repsonse(FriendReplyRespPacket friendReplyRespPacket) {
        ChannelFuture future = channel.writeAndFlush(friendReplyRespPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    LOGGER.warn("添加好友回复响应包发送失败");
                }
            }
        });
    }
}
