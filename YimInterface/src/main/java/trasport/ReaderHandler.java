package trasport;

import common.Packet;
import interfaces.Connection;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import interfaces.SubPacket;

/**
 * 最终读取数据的Handler
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class ReaderHandler extends ChannelInboundHandlerAdapter {
    private Connection conn;

    public ReaderHandler(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 与服务器连接成功
        conn.onFinishConnect(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet) {
            Packet packet = (Packet) msg;
            new SubPacket(packet, conn).deal();
        }
    }
}
