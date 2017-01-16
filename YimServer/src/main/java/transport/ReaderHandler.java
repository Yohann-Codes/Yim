package transport;

import dispatch.Dispatch;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.Packet;

import java.util.concurrent.ExecutorService;

/**
 * 最终读取数据的Handler
 * <p>o
 * Created by yohann on 2017/1/9.
 */
public class ReaderHandler extends ChannelInboundHandlerAdapter {
    private Channel channel;

    private ExecutorService tPool;

    public ReaderHandler(ExecutorService tPool) {
        this.tPool = tPool;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet) {
            // 接收的数据包
            Packet packet = (Packet) msg;
            // 将数据包传递给线程池处理
            if (tPool != null) {
                tPool.execute(new Dispatch(packet, channel));
            }
        }
    }
}
