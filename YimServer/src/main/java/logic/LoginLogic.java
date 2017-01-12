package logic;

import dao.OfflineMsgDao;
import dao.UserDao;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.ReferenceCountUtil;
import log.MyLog;
import packet.ChatPacket;
import packet.LoginPacket;
import packet.RespLoginPacket;
import packet.UserInfoPacket;
import transport.HeartbeatHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 登录逻辑处理
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class LoginLogic {
    private LoginPacket loginPacket;
    private Channel channel;
    private String username;

    public LoginLogic(LoginPacket loginPacket, Channel channel) {
        this.loginPacket = loginPacket;
        this.channel = channel;
    }

    /**
     * 登录验证
     */
    public void deal() {
        username = loginPacket.getUsername();
        RespLoginPacket respLoginPacket = new RespLoginPacket();
        respLoginPacket.setUsername(username);
        try {
            UserDao userDao = new UserDao();
            List<UserInfoPacket> users = userDao.queryByUsername(username);
            if (users.size() > 0) {
                if (loginPacket.getPassword().equals(users.get(0).getPassword())) {
                    success(respLoginPacket);
                } else {
                    defeat(respLoginPacket);
                }
            } else {
                defeat(respLoginPacket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(loginPacket);
        }
    }

    /**
     * 发送响应，登录成功，维护连接
     *
     * @param respLoginPacket
     */
    public void success(RespLoginPacket respLoginPacket) {
        boolean result = ConnPool.add(username, channel);
        if (result) {
            respLoginPacket.setSuccessful(true);
            ChannelFuture future = channel.writeAndFlush(respLoginPacket);
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        MyLog.userLogger(username + " 登录成功");
                        // 开启心跳检测
                        MyLog.userLogger(username + " 开启心跳检测");
                        channel.pipeline().addAfter("IdleStateHandler",
                                "HeartbeatHandler", new HeartbeatHandler(channel));
                        // 发送离线消息
                        sendOfflineMsg();
                    } else {
                        ConnPool.remove(username);
                    }
                }
            });
        } else {
            defeat(respLoginPacket);
        }
    }

    /**
     * 发送响应，登录失败，关闭连接
     *
     * @param respLoginPacket
     */
    public void defeat(RespLoginPacket respLoginPacket) {
        respLoginPacket.setSuccessful(false);
        ChannelFuture future = channel.writeAndFlush(respLoginPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    channel.close();
                    MyLog.userLogger(username + " 登录失败");
                }
            }
        });
    }

    /**
     * 发送离线消息
     */
    public void sendOfflineMsg() {
        try {
            // 查询消息
            List<ChatPacket> chatPackets = new OfflineMsgDao().queryMsg(username);
            if (chatPackets.size() != 0) {
                // 一个一个发送
                for (int i = 0; i < chatPackets.size(); i++) {
                    ChatPacket chatPacket = chatPackets.get(i);
                    channel.writeAndFlush(chatPacket);
                    MyLog.sysLogger("离线消息 " + chatPacket.getSrcUsername()
                            + "-->" + chatPacket.getDesUsername() + " 发送成功");
                }
                // 删除离线消息
                int row = new OfflineMsgDao().deleteMsg(username);
                if (row == chatPackets.size()) {
                    MyLog.sysLogger("删除离线消息 成功");
                } else {
                    MyLog.sysLogger("删除离线消息 失败");
                }
            } else {
                return;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
