package interfaces;

import common.Constants;
import io.netty.channel.ChannelHandlerContext;
import packet.LoginPacket;

/**
 * 登录接口
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class Login extends Connection {

    /**
     * 连接服务器
     */
    public Login() {
        super(Constants.HOST, Constants.PORT);
    }

    @Override
    public void onFinishConnect(ChannelHandlerContext ctx) {
        System.out.println("连接成功 --> 开始登录");
        // 登录
        doLogin(ctx, "yohann", "456");
        // 启动心跳
//        ctx.pipeline().addAfter("IdleStateHandler", "HeartbeatHandler", new HeartbeatHandler());
    }

    public void doLogin(ChannelHandlerContext ctx, String username, String password) {
        // 创建数据包
        LoginPacket loginPacket = new LoginPacket();
        loginPacket.setUsername(username);
        loginPacket.setPassword(password);

        // 发送数据包
        ctx.writeAndFlush(loginPacket);
    }
}
