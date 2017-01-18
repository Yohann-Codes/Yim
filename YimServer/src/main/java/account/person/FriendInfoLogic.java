package account.person;

import bean.UserBean;
import dao.FriendDao;
import dao.UserDao;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * 查看好友信息逻辑
 *
 * Created by yohann on 2017/1/18.
 */
public class FriendInfoLogic {
    private static final Logger LOGGER = Logger.getLogger(FriendInfoLogic.class);

    private String username;
    private String friend;
    private Channel channel;

    public FriendInfoLogic(FriendInfoReqPacket friendInfoReqPacket, Channel channel) {
        this.username = friendInfoReqPacket.getUsername();
        this.friend = friendInfoReqPacket.getFriend();
        this.channel = channel;
    }

    public void deal() {
        // 查询是否为好友
        UserDao userDao = null;
        FriendDao friendDao = null;
        try {
            userDao = new UserDao();
            friendDao = new FriendDao();
            String c = friendDao.queryColumnByFri(username, friend);
            if (c != null) {
                // 查询信息
                List<UserBean> users = userDao.queryByUsername(friend);
                if (users.size() == 1) {
                    UserBean userBean = users.get(0);
                    success(userBean);
                } else {
                    // 数据库异常
                    defeat("数据库异常");
                    LOGGER.warn("查询好友信息 数据库异常");
                }
            } else {
                // 未添加好友
                defeat("与该用户不是好友，不能查看陌生人的信息");
                LOGGER.info("查询好友信息 失败 不是好友");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("数据库连接异常");
        } catch (SQLException e) {
            LOGGER.warn("数据库连接异常");
        } finally {
            if (userDao != null) {
                userDao.close();
            }
            if (friendDao != null) {
                friendDao.close();
            }
        }
    }

    private void success(final UserBean userBean) {
        FriendInfoRespPacket friendInfoRespPacket =
                new FriendInfoRespPacket(true, null,
                        userBean.getUsername(),
                        userBean.getName(),
                        userBean.getSex(),
                        userBean.getAge(),
                        userBean.getPhone(),
                        userBean.getAddress(),
                        userBean.getIntroduction());
        ChannelFuture future = channel.writeAndFlush(friendInfoRespPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                LOGGER.info(username + " 查询好友信息 成功");
            }
        });
    }

    private void defeat(String hint) {
        channel.writeAndFlush(new InfoLookRespPacket(false, hint,
                null, null, null, null, null, null, null));
    }
}
