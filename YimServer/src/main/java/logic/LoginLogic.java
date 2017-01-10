package logic;

import dao.UserDao;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import log.MyLog;
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
    private ChannelHandlerContext ctx;
    private String username;

    public LoginLogic(LoginPacket loginPacket, ChannelHandlerContext ctx) {
        this.loginPacket = loginPacket;
        this.ctx = ctx;
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
        boolean result = ConnPool.add(username, ctx);
        if (result) {
            respLoginPacket.setSuccessful(true);
            ChannelFuture future = ctx.writeAndFlush(respLoginPacket);
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        MyLog.userLogger(username + " 登录成功");
                        // 开启心跳检测
                        MyLog.userLogger(username + " 开启心跳检测");
                        ctx.pipeline().addAfter("IdleStateHandler",
                                "HeartbeatHandler", new HeartbeatHandler(ctx));
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
        ChannelFuture future = ctx.writeAndFlush(respLoginPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    ctx.channel().close();
                    MyLog.userLogger(username + " 登录失败");
                }
            }
        });
    }
}
