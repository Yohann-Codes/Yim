package account.person;

import connection.ConnPool;
import dao.FriendDao;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查看已添加好友逻辑
 *
 * Created by yohann on 2017/1/18.
 */
public class AllFriendLogic {
    private static final Logger LOGGER = Logger.getLogger(AllFriendLogic.class);

    private String username;
    private Channel channel;

    public AllFriendLogic(AllFriendReqPacket allFriendReqPacket, Channel channel) {
        username = allFriendReqPacket.getUsername();
        this.channel = channel;
    }

    public void deal() {
        FriendDao friendDao = null;
        try {
            friendDao = new FriendDao();
            List<String> friends = friendDao.queryAllFri(username);
            AllFriendRespPacket allFriendRespPacket = null;
            if (friends.size() != 0) {
                Map<String, Boolean> friMap = new HashMap<String, Boolean>();
                for (int i = 0; i < friends.size(); i++) {
                    String friend = friends.get(i);
                    // 查询好友是否在线
                    if (ConnPool.query(friend) != null) {
                        // 在线
                        friMap.put(friend, true);
                    } else {
                        // 离线
                        friMap.put(friend, false);
                    }
                }
                allFriendRespPacket = new AllFriendRespPacket(true, null, friMap);
            } else {
                // 没有好友
                allFriendRespPacket = new AllFriendRespPacket(false, null, null);
            }

            ChannelFuture future = channel.writeAndFlush(allFriendRespPacket);
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    LOGGER.info(username + " 查询已添加好友 成功");
                }
            });

        } catch (ClassNotFoundException e) {
            LOGGER.warn("数据库连接异常");
        } catch (SQLException e) {
            LOGGER.warn("数据库连接异常");
        } finally {
            if (friendDao != null) {
                friendDao.close();
            }
        }
    }
}
