package groups;

import dao.GroupDao;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * 讨论组创建逻辑
 * <p>
 * Created by yohann on 2017/1/19.
 */
public class GroupCreateLogic {
    private static final Logger LOGGER = Logger.getLogger(GroupCreateLogic.class);

    private String username;
    private String groupName;
    private Channel channel;

    public GroupCreateLogic(GroupCreateReqPacket groupCreateReqPacket, Channel channel) {
        username = groupCreateReqPacket.getUsername();
        groupName = groupCreateReqPacket.getGroupName();
        this.channel = channel;
    }

    public void deal() {
        // 讨论组名称唯一
        GroupDao groupDao = null;
        try {
            groupDao = new GroupDao();
            List<String> members = groupDao.queryMemberByGroupName(groupName);
            if (members == null) {
                int r = groupDao.insertGroup(groupName, username);
                if (r == 1) {
                    success();
                    LOGGER.info(username + " 创建讨论组 " + groupName + " 成功");
                } else {
                    defeat("数据库错误");
                    LOGGER.warn("数据库错误");
                }
            } else {
                // 讨论组名字已存在
                defeat("讨论组名称已存在");
                LOGGER.info(username + " 创建讨论组 " + groupName + " 失败（讨论组名称已存在）");
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

    public void success() {
        GroupCreateRespPacket groupCreateRespPacket = new GroupCreateRespPacket(true, groupName, null);
        channel.writeAndFlush(groupCreateRespPacket);
    }

    public void defeat(String hint) {
        GroupCreateRespPacket groupCreateRespPacket = new GroupCreateRespPacket(false, groupName, hint);
        channel.writeAndFlush(groupCreateRespPacket);
    }
}
