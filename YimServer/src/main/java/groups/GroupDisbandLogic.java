package groups;

import dao.GroupDao;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * 解散讨论组逻辑
 * <p>
 * Created by yohann on 2017/1/19.
 */
public class GroupDisbandLogic {
    private static final Logger LOGGER = Logger.getLogger(GroupCreateLogic.class);

    private String username;
    private String groupName;
    private Channel channel;

    public GroupDisbandLogic(GroupDisbandReqPacket groupDisbandReqPacket, Channel channel) {
        username = groupDisbandReqPacket.getUsername();
        groupName = groupDisbandReqPacket.getGroupName();
        this.channel = channel;
    }

    public void deal() {
        // 查询讨论组是否存在
        GroupDao groupDao = null;
        try {
            groupDao = new GroupDao();
            List<String> members = groupDao.queryMemberByGroupName(groupName);
            if (members != null) {
                if (members.get(0).equals(username)) {
                    int r = groupDao.removeGroup(groupName);
                    if (r == 1) {
                        success();
                        LOGGER.info(username + " 解散讨论组 " + groupName + " 成功");
                    } else {
                        defeat("数据库错误");
                        LOGGER.warn("数据库错误");
                    }
                } else {
                    // 非创建者操作
                    defeat("非讨论创建者不能执行解散操作");
                }
            } else {
                // 讨论组不存在
                defeat("讨论组不存在");
                LOGGER.info(username + " 解散讨论组 " + groupName + " 失败（讨论组不存在）");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("数据库连接异常", e);
        } catch (SQLException e) {
            LOGGER.warn("数据库连接异常", e);
        } finally {
            if (groupDao != null) {
                groupDao.close();
            }
        }
    }

    private void success() {
        GroupDisbandRespPacket groupDisbandRespPacket = new GroupDisbandRespPacket(true, null, groupName);
        channel.writeAndFlush(groupDisbandRespPacket);
    }

    private void defeat(String hint) {
        GroupDisbandRespPacket groupDisbandRespPacket = new GroupDisbandRespPacket(false, hint, groupName);
        channel.writeAndFlush(groupDisbandRespPacket);
    }
}
