package interfaces;

import common.Constants;
import io.netty.channel.ChannelHandlerContext;
import packet.LoginPacket;
import trasport.Service;

/**
 * 登录接口
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class Login extends Connection {
    private String username;
    private String password;

    /**
     * 连接服务器
     */
    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 客户端调用此方法进行登录
     */
    public void execute() {
        service = new Service(this);
        service.connect(Constants.HOST, Constants.PORT);
    }

    @Override
    public void onFinishConnect(ChannelHandlerContext ctx) {
        System.out.println("连接成功 --> 开始登录");
        // 登录
        doLogin(ctx, username, password);
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
