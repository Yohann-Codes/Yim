package trasport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.HeartbeatPacket;

/**
 * 心跳Handler
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 向服务器发送心跳包
        HeartbeatPacket heartbeatPacket = new HeartbeatPacket();
    }
}
