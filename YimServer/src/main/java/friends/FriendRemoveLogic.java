package friends;

import dao.FriendDao;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * 删除好友逻辑
 * <p>
 * Created by yohann on 2017/1/18.
 */
public class FriendRemoveLogic {
    private static final Logger LOGGER = Logger.getLogger(FriendRemoveLogic.class);

    private FriendRemoveReqPacket friendRemoveReqPacket;
    private Channel channel;

    public FriendRemoveLogic(FriendRemoveReqPacket friendRemoveReqPacket, Channel channel) {
        this.friendRemoveReqPacket = friendRemoveReqPacket;
        this.channel = channel;
    }

    public void deal() {
        String username = friendRemoveReqPacket.getUsername();
        String friend = friendRemoveReqPacket.getFriend();

        FriendDao friendDao = null;

        try {
            friendDao = new FriendDao();
            // 双方删除好友
            String c1 = friendDao.queryColumnByFri(username, friend);
            String c2 = friendDao.queryColumnByFri(friend, username);
            if (c1 != null && c2 != null) {
                // 删除
                int r1 = friendDao.removeFriend(username, c1);
                int r2 = friendDao.removeFriend(friend, c2);
                if (r1 == 1 && r2 == 1) {
                    LOGGER.info(username + " 删除好友 " + friend + " 操作成功");
                    success(friend);
                } else {
                    LOGGER.warn("数据库错误");
                    defeat("数据库错误", friend);
                }
            } else {
                // 无此好友
                LOGGER.info(username + " 删除好友 " + friend + " 操作失败（没有添加该好友）");
                defeat("没有添加该好友", friend);
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("MySQL连接异常", e);
        } catch (SQLException e) {
            LOGGER.warn("MySQL连接异常", e);
        } finally {
            if (friendDao != null) {
                friendDao.close();
            }
        }
    }

    private void success(String friend) {
        FriendRemoveRespPacket friendRemoveRespPacket = new FriendRemoveRespPacket(true, null, friend);
        channel.writeAndFlush(friendRemoveRespPacket);
    }

    private void defeat(String hint, String friend) {
        FriendRemoveRespPacket friendRemoveRespPacket = new FriendRemoveRespPacket(false, hint, friend);
        channel.writeAndFlush(friendRemoveRespPacket);
    }
}
