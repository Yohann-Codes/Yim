package message.group;

import connection.ConnPool;
import dao.GroupDao;
import dao.OfflineMsgGroupDao;
import dao.UserDao;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.ReferenceCountUtil;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 讨论组消息逻辑
 * <p>
 * Created by yohann on 2017/1/20.
 */
public class GroupMsgLogic {
    private static final Logger LOGGER = Logger.getLogger(GroupMsgLogic.class);

    private GroupMsgReqPacket groupMsgReqPacket;
    private String sender;
    private String groupName;
    private String message;
    private long time;

    private Channel senChannel;

    // 发送结果，只要有一个发送失败就置为false
    private boolean r1 = true, r2 = true;
    private String h1 = null, h2 = null;

    public GroupMsgLogic(GroupMsgReqPacket groupMsgReqPacket, Channel channel) {
        this.groupMsgReqPacket = groupMsgReqPacket;
        sender = groupMsgReqPacket.getUsername();
        groupName = groupMsgReqPacket.getGroupName();
        message = groupMsgReqPacket.getMessage();
        time = groupMsgReqPacket.getTime();
        this.senChannel = channel;
    }

    public void deal() {
        // 先在内存中找group
        List<String> members = GroupManager.groupsQuery(groupName);
        if (members != null) {
            // 修改讨论组最后一次活跃时间
            GroupManager.groupTimesUpdate(groupName, time);
            LOGGER.info("内存 讨论组<" + groupName + "> 修改时间戳");
            for (int i = 0; i < members.size(); i++) {
                String member = members.get(i);
                if (!member.equals(sender)) {
                    // 不给自己发送
                    sendMessage(member);
                }
            }
        } else {
            // 查询数据库
            GroupDao groupDao = null;
            try {
                groupDao = new GroupDao();
                members = groupDao.queryMemberByGroupName(groupName);
                if (members != null) {
                    // 添加讨论组
                    GroupManager.groupsAdd(groupName, members);
                    GroupManager.groupTimesAdd(groupName, time);
                    LOGGER.info("数据库 讨论组<" + groupName + "> 维护在内存");
                    for (int i = 0; i < members.size(); i++) {
                        String member = members.get(i);
                        if (!member.equals(sender)) {
                            // 不给自己发送
                            sendMessage(member);
                        }
                    }
                } else {
                    // 讨论组不存在
                    LOGGER.info("讨论组消息 " + sender + "-->" + groupName + " 发送失败（讨论组不存在）");
                    r1 = false;
                    h1 = "讨论组不存在";
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

        // 发送响应
        if (r1 && r2) {
            response(new GroupMsgRespPacket(true, null));
        } else {
            if (h1 != null) {
                response(new GroupMsgRespPacket(false, h1));
            }
            if (h2 != null) {
                response(new GroupMsgRespPacket(false, h2));
            }
        }
    }

    /**
     * 发送消息
     *
     * @param member
     */
    private void sendMessage(String member) {
        // 在Online用户中查找，ConnPool
        Channel recChannel = ConnPool.query(member);
        if (recChannel != null) {
            online(recChannel);
        } else {
            // 在Offline用户中查找，数据库
            offline(member);
        }
    }

    /**
     * 在线消息，直接发给消息接收者
     *
     * @param recChannel
     */
    private void online(Channel recChannel) {
        ChannelFuture future = recChannel.writeAndFlush(groupMsgReqPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                LOGGER.info("讨论组消息 " + sender + "-->" + groupName + " 转发成功");
            }
        });
    }

    /**
     * 离线消息，先存入数据库，待用户上线时发送
     * @param member
     */
    private void offline(String member) {
        UserDao userDao = null;
        OfflineMsgGroupDao offlineMsgGroupDao = null;
        try {
            userDao = new UserDao();
            offlineMsgGroupDao = new OfflineMsgGroupDao();
            int row = offlineMsgGroupDao
                    .insertMsg(sender, member, groupName, message, new Timestamp(time));
            if (row == 1) {
                LOGGER.info("讨论组离线消息 " + sender + "-->" + groupName + "-->"+ member + " 存储成功");
            } else {
                LOGGER.warn("数据库错误");
                r2 = false;
                h2 = "数据库错误";
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("MySQL连接异常", e);
        } catch (SQLException e) {
            LOGGER.warn("MySQL连接异常", e);
        } finally {
            if (userDao != null) {
                userDao.close();
            }
            if (offlineMsgGroupDao != null) {
                offlineMsgGroupDao.close();
            }
        }
    }

    /**
     * 向客户端发送响应包
     */
    public void response(final GroupMsgRespPacket groupMsgRespPacket) {
        ChannelFuture future = senChannel.writeAndFlush(groupMsgRespPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    ReferenceCountUtil.release(groupMsgRespPacket);
                } else {
                    LOGGER.warn("讨论组消息响应包发送失败");
                }
            }
        });
    }
}
