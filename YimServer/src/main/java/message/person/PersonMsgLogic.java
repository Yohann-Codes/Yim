package message.person;

import bean.UserBean;
import connection.ConnPool;
import dao.OfflineMsgDao;
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
 * 个人聊天数据处理逻辑
 * <p>
 * Created by yohann on 2017/1/16.
 */
public class PersonMsgLogic {
    private static final Logger LOGGER = Logger.getLogger(PersonMsgLogic.class);

    private PersonMsgReqPacket personMsgReqPacket;
    private String sender;
    private String receiver;
    private Channel senChannel;

    public PersonMsgLogic(PersonMsgReqPacket personMsgReqPacket, Channel channel) {
        this.personMsgReqPacket = personMsgReqPacket;
        senChannel = channel;
    }

    public void deal() {
        sender = personMsgReqPacket.getUsername();
        receiver = personMsgReqPacket.getReceiver();
        String message = personMsgReqPacket.getMessage();
        long time = personMsgReqPacket.getTime();

        // 在Online用户中查找，ConnPool
        Channel recChannel = ConnPool.query(receiver);
        if (recChannel != null) {
            online(recChannel, personMsgReqPacket);
        } else {
            // 在Offline用户中查找，数据库
            offline(message, time);
        }
    }

    /**
     * 在线消息，直接发给消息接收者
     *
     * @param recChannel
     * @param personMsgReqPacket
     */
    private void online(Channel recChannel, PersonMsgReqPacket personMsgReqPacket) {
        ChannelFuture future = recChannel.writeAndFlush(personMsgReqPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    LOGGER.info("在线消息 " + sender + "-->" + receiver + " 转发成功");
                    response(new PersonMsgRespPacket(true, null));
                } else {
                    LOGGER.info("在线消息 " + sender + "-->" + receiver + " 转发失败");
                    response(new PersonMsgRespPacket(false, "网络异常"));
                }
            }
        });
    }

    /**
     * 离线消息，先存入数据库，待用户上线时发送
     *
     * @param message
     * @param time
     */
    private void offline(String message, long time) {
        UserDao userDao = null;
        OfflineMsgDao offlineMsgDao = null;
        try {
            userDao = new UserDao();
            offlineMsgDao = new OfflineMsgDao();
            // 查询是否存在该用户
            List<UserBean> users = userDao.queryByUsername(receiver);
            if (users.size() == 1) {
                int row = offlineMsgDao
                        .insertMsg(sender, receiver, message, new Timestamp(time));
                if (row == 1) {
                    LOGGER.info("离线消息 " + sender + "-->" + receiver + " 存储成功");
                    response(new PersonMsgRespPacket(true, null));
                } else {
                    LOGGER.warn("数据库错误");
                    response(new PersonMsgRespPacket(false, "数据库错误"));
                }
            } else {
                LOGGER.info("离线消息 " + sender + "-->" + receiver + " 存储失败(用户不存在)");
                response(new PersonMsgRespPacket(false, "用户不存在"));
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("MySQL连接异常", e);
        } catch (SQLException e) {
            LOGGER.warn("MySQL连接异常", e);
        } finally {
            userDao.close();
            offlineMsgDao.close();
        }
    }

    /**
     * 向客户端发送响应包
     */
    public void response(PersonMsgRespPacket personMsgRespPacket) {
        ChannelFuture future = senChannel.writeAndFlush(personMsgRespPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    ReferenceCountUtil.release(personMsgReqPacket);
                } else {
                    LOGGER.warn("消息响应包发送失败");
                }
            }
        });
    }
}
