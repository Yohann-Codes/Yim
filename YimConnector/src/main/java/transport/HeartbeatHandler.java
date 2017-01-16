package transport;

import common.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
        // 向服务器发送心跳包
        HeartbeatPacket heartbeatPacket = new HeartbeatPacket(username);
        UserInfo.channel.writeAndFlush(heartbeatPacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与服务器断开连接");
    }
}
