package groups;

import bean.UserBean;
import dao.GroupDao;
import dao.UserDao;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * 讨论组邀请成员逻辑
 *
 * Created by yohann on 2017/1/19.
 */
public class MemberInviteLogic {
    private static final Logger LOGGER = Logger.getLogger(MemberInviteLogic.class);

    private String username;
    private String groupName;
    private String responser;
    private Channel channel;

    public MemberInviteLogic(MemberInviteReqPacket memberInviteReqPacket, Channel channel) {
        username = memberInviteReqPacket.getUsername();
        groupName = memberInviteReqPacket.getGroupName();
        responser = memberInviteReqPacket.getResponser();
        this.channel = channel;
    }

    public void deal() {
        UserDao userDao = null;
        GroupDao groupDao = null;
        try {
            userDao = new UserDao();
            groupDao = new GroupDao();
            // 讨论组是否存在
            List<String> members = groupDao.queryMemberByGroupName(groupName);
            if (members != null) {
                // 用户是否存在
                List<UserBean> users = userDao.queryByUsername(responser);
                if (users.size() == 1) {
                    // 邀请者是否为创建者
                    if (members.get(0).equals(username)) {
                        // 添加成员
                        String column = groupDao.queryNoMemColumn(groupName);
                        int r = groupDao.insertMember(groupName, responser, column);
                        if (r == 1) {
                            success();
                            LOGGER.info("邀请进入讨论组<" + groupName + "> " + username + "-->" + responser + " 成功");
                        }
                    } else {
                        defeat("非讨论组创建者不能邀请成员");
                        LOGGER.info("邀请进入讨论组<" + groupName + "> " + username + "-->" + responser + " 失败（非讨论组创建者不能邀请成员）");
                    }
                } else {
                    defeat("用户不存在");
                    LOGGER.info("邀请进入讨论组<" + groupName + "> " + username + "-->" + responser + " 失败（用户不存在）");
                }
            } else {
                defeat("讨论组不存在");
                LOGGER.info("邀请进入讨论组<" + groupName + "> " + username + "-->" + responser + " 失败（讨论组不存在）");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("数据库连接异常");
        } catch (SQLException e) {
            LOGGER.warn("数据库连接异常");
        } finally {
            if (userDao != null) {
                userDao.close();
            }
            if (groupDao != null) {
                groupDao.close();
            }
        }

    }

    private void success() {
        MemberInviteRespPacket memberInviteRespPacket = new MemberInviteRespPacket(true, null);
        channel.writeAndFlush(memberInviteRespPacket);
    }

    private void defeat(String hint) {
        MemberInviteRespPacket memberInviteRespPacket = new MemberInviteRespPacket(false, hint);
        channel.writeAndFlush(memberInviteRespPacket);
    }
}
