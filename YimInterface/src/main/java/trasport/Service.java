package trasport;

import interfaces.Connection;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 网络传输服务
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class Service {
    private Connection conn;

    public Service(Connection conn) {
        this.conn = conn;
    }

    /**
     * 启动服务，连接服务器
     *
     * @param host
     * @param port
     */
    public void connect(String host, int port) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("PacketCodec", new PacketCodec());
                            pipeline.addLast("IdleStateHandler", new IdleStateHandler(0, 5, 0));
                            pipeline.addLast("ReaderHandler", new ReaderHandler(conn));
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
