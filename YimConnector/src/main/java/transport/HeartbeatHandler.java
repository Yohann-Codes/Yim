package transport;

import account.login.Login;
import common.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import packet.HeartbeatPacket;

/**
 * 心跳Handler
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    private String username;

    public HeartbeatHandler(String username) {
        this.username = username;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 向服务器发送心跳包
            HeartbeatPacket heartbeatPacket = new HeartbeatPacket(username);
            UserInfo.channel.writeAndFlush(heartbeatPacket);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (UserInfo.isLogout) {
            System.out.println("退出登录");
        } else {
            // 意外断线，执行重连
            System.out.println("与服务器断开连接，正在尝试重新连接......");
            if (UserInfo.token != null) {
                new Login(UserInfo.token).execute();
            } else {
                System.out.println("登录信息已失效，请重新登录");
            }
        }
    }
}
