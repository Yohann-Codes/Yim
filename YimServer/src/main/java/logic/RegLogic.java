package logic;

import dao.UserDao;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.ReferenceCountUtil;
import log.MyLog;
import packet.RegisterPacket;
import packet.RespRegPacket;
import packet.UserInfoPacket;

import java.sql.SQLException;
import java.util.List;

/**
 * 注册逻辑处理
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class RegLogic {
    private RegisterPacket registerPacket;
    private Channel channel;
    private String username;

    public RegLogic(RegisterPacket registerPacket, Channel channel) {
        this.registerPacket = registerPacket;
        this.channel = channel;
    }

    /**
     * 注册验证
     */
    public void deal() {
        username = registerPacket.getUsername();
        RespRegPacket respRegPacket = new RespRegPacket();
        respRegPacket.setUsername(username);
        try {
            // 查询
            List<UserInfoPacket> users = new UserDao().queryByUsername(username);
            if (users.size() == 0) {
                int row = new UserDao().insertUser(username, registerPacket.getPassword());
                if (row != 0) {
                    // 注册成功
                    success(respRegPacket);
                } else {
                    // 注册失败
                    defeat(respRegPacket);
                }
            } else {
                // 注册失败
                defeat(respRegPacket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(registerPacket);
        }
    }

    /**
     * 注册成功，发送响应，关闭连接
     *
     * @param respRegPacket
     */
    public void success(RespRegPacket respRegPacket) {
        respRegPacket.setSuccessful(true);
        ChannelFuture future = channel.writeAndFlush(respRegPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    channel.close();
                    MyLog.userLogger(username + " 注册成功");
                }
            }
        });
    }

    /**
     * 登录失败，发送响应，关闭连接
     *
     * @param respRegPacket
     */
    public void defeat(RespRegPacket respRegPacket) {
        respRegPacket.setSuccessful(false);
        ChannelFuture future = channel.writeAndFlush(respRegPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    channel.close();
                    MyLog.userLogger(username + " 注册失败");
                }
            }
        });
    }
}
