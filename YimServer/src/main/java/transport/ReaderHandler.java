package transport;

import common.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import logic.SubPacket;

import java.util.concurrent.ExecutorService;

/**
 * 最终读取数据的Handler
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class ReaderHandler extends ChannelInboundHandlerAdapter {

    private ExecutorService tPool;

    public ReaderHandler(ExecutorService tPool) {
        this.tPool = tPool;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet) {
            // 接收的数据包
            Packet packet = (Packet) msg;
            // 将数据包传递给线程池处理
            if (tPool != null) {
                tPool.execute(new SubPacket(packet, ctx));
            }
        }
    }
}