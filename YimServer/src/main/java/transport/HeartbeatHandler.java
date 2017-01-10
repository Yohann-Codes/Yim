package transport;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import log.MyLog;
import logic.ConnPool;
import packet.HeartbeatPacket;

/**
 * 心跳检测Handler
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    // Readerhandler的ctx
    private ChannelHandlerContext readerCtx;
    private String username;

    // 丢失的心跳数
    private int counter = 0;

    public HeartbeatHandler(ChannelHandlerContext ctx) {
        readerCtx = ctx;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (username == null) {
            username = ConnPool.query(readerCtx);
        }
        // 心跳丢失
        counter++;
        MyLog.userLogger(username + " 丢失" + counter + "个心跳包");
        if (counter > 4) {
            // 心跳丢失数达到5个，主动断开连接
            ctx.channel().close();
            // 将该连接从连接池中删除
            MyLog.userLogger(username + " 与服务器断开连接");
            ConnPool.remove(username);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ConnPool.remove(username);
        MyLog.userLogger(username + " 断开连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HeartbeatPacket) {
            if (username == null) {
                username = ConnPool.query(readerCtx);
            }
            // 心跳丢失清零
            counter = 0;
            MyLog.userLogger(username + " 收到心跳包");
            ReferenceCountUtil.release(msg);
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}