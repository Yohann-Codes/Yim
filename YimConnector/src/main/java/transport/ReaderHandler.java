package transport;

import account.OnConnectionListener;
import dispatch.Dispatch;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.Packet;

/**
 * 最终读取数据的Handler
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class ReaderHandler extends ChannelInboundHandlerAdapter {
    private OnConnectionListener connListener;

    public ReaderHandler(OnConnectionListener connListener) {
        this.connListener = connListener;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 与服务器连接成功
        connListener.onConnetionComplete();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Packet) {
            Packet packet = (Packet) msg;
            // 分发数据包
            new Dispatch(packet).deal();
        }
    }
}
