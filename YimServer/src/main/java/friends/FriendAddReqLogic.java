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
 * 添加好友逻辑
 * <p>
 * Created by yohann on 2017/1/17.
 */
public class FriendAddReqLogic {
    private static final Logger LOGGER = Logger.getLogger(FriendAddReqLogic.class);

    private FriendAddReqPacket friendAddReqPacket;
    private Channel channel;
    private String requester;
    private String responser;

    public FriendAddReqLogic(FriendAddReqPacket friendAddReqPacket, Channel channel) {
        this.friendAddReqPacket = friendAddReqPacket;
        this.channel = channel;
    }

    public void deal() {
        requester = friendAddReqPacket.getUsername();
        responser = friendAddReqPacket.getResponser();
        String info = friendAddReqPacket.getInfo();

        UserDao userDao = null;
        FriendDao friendDao = null;
        FriendReqDao friendReqDao = null;
        try {
            userDao = new UserDao();
            friendDao = new FriendDao();
            friendReqDao = new FriendReqDao();

            // 查询是否存在该用户
            List<UserBean> users = userDao.queryByUsername(responser);
            if (users.size() == 1) {
                // 查询sender的好友数量是否达到上线
                String column = friendDao.queryNoFriColumn(requester);
                if (column != null) {
                    // 将请求信息添加到数据库
                    int row = friendReqDao.insertFriendReq(requester, responser, info);
                    if (row == 1) {
                        // 成功
                        // 查看responser是否在线
                        Channel recChannel = ConnPool.query(responser);
                        if (recChannel != null) {
                            // 发送
                            ChannelFuture future = recChannel.writeAndFlush(friendAddReqPacket);
                            future.addListener(new ChannelFutureListener() {
                                public void operationComplete(ChannelFuture future) throws Exception {
                                    if (future.isSuccess()) {
                                        // 转发成功
                                        LOGGER.info("添加好友请求 " + requester + " --> " + responser + " 转发成功");
                                        success();
                                    } else {
                                        LOGGER.warn("添加好友请求 " + requester + " --> " + responser + " 转发失败");
                                    }
                                }
                            });
                        } else {
                            LOGGER.info("添加好友请求 " + requester + " --> " + responser + " 存储成功");
                            success();
                        }
                    } else {
                        // 失败
                        LOGGER.warn("添加好友请求 " + requester + " --> " + responser + " 存储失败");
                        defeat("数据库错误");
                    }
                } else {
                    // 好友数量爆满
                    defeat("好友数量爆满");
                    LOGGER.info("添加好友请求 " + requester + " --> " + responser + " 添加失败（好友数量爆满）");
                }
            } else {
                // 不存在该用户
                defeat("不存在该用户");
                LOGGER.info("添加好友请求 " + requester + " --> " + responser + " 添加失败（不存在该用户）");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("MySQL连接异常", e);
        } catch (SQLException e) {
            LOGGER.warn("MySQL连接异常", e);
        } finally {
            if (friendDao != null) {
                friendDao.close();
            }
            if (friendReqDao != null) {
                friendReqDao.close();
            }
        }
    }

    private void success() {
        FriendAddRespPacket friendAddRespPacket = new FriendAddRespPacket(true, null);
        repsonse(friendAddRespPacket);
    }

    private void defeat(String hint) {
        FriendAddRespPacket friendAddRespPacket = new FriendAddRespPacket(false, hint);
        repsonse(friendAddRespPacket);
    }

    /**
     * 发送响应
     *
     * @param friendAddRespPacket
     */
    private void repsonse(FriendAddRespPacket friendAddRespPacket) {
        ChannelFuture future = channel.writeAndFlush(friendAddRespPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    LOGGER.warn("申请添加好友响应包发送失败");
                }
            }
        });
    }
}
