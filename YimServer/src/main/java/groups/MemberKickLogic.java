package groups;

import dao.GroupDao;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * 讨论组邀请成员逻辑
 * <p>
 * Created by yohann on 2017/1/19.
 */
public class MemberKickLogic {
    private static final Logger LOGGER = Logger.getLogger(MemberKickLogic.class);

    private String username;
    private String groupName;
    private String member;
    private Channel channel;

    public MemberKickLogic(MemberKickReqPacket memberKickReqPacket, Channel channel) {
        username = memberKickReqPacket.getUsername();
        groupName = memberKickReqPacket.getGroupName();
        member = memberKickReqPacket.getMember();
        this.channel = channel;
    }

    public void deal() {
        GroupDao groupDao = null;
        try {
            groupDao = new GroupDao();
            // 讨论组是否存在
            List<String> members = groupDao.queryMemberByGroupName(groupName);
            if (members != null) {
                // 成员是否存在
                if (members.contains(member)) {
                    // 邀请者是否为创建者
                    if (members.get(0).equals(username)) {
                        // 删除成员
                        String column = groupDao.queryColumnByMem(groupName, member);
                        int r = groupDao.removeMember(groupName, column);
                        if (r == 1) {
                            success();
                            LOGGER.info("讨论组<" + groupName + "> " + "踢出成员 " + member + " 成功");
                        }
                    } else {
                        defeat("非讨论组创建者不能踢出成员");
                        LOGGER.info("讨论组<" + groupName + "> " + "踢出成员 " + member + " 失败（非讨论组创建者不能踢出成员）");
                    }
                } else {
                    defeat("成员不存在");
                    LOGGER.info("讨论组<" + groupName + "> " + "踢出成员 " + member + " 失败（成员不存在）");
                }
            } else {
                defeat("讨论组不存在");
                LOGGER.info("讨论组<" + groupName + "> " + "踢出成员 " + member + " 失败（讨论组不存在）");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("数据库连接异常");
        } catch (SQLException e) {
            LOGGER.warn("数据库连接异常");
        } finally {
            if (groupDao != null) {
                groupDao.close();
            }
        }

    }

    private void success() {
        MemberKickRespPacket memberKickRespPacket = new MemberKickRespPacket(true, null, groupName, member);
        channel.writeAndFlush(memberKickRespPacket);
    }

    private void defeat(String hint) {
        MemberKickRespPacket memberKickRespPacket = new MemberKickRespPacket(false, hint, null, null);
        channel.writeAndFlush(memberKickRespPacket);
    }
}
