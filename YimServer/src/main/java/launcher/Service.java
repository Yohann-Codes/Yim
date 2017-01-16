package launcher;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.log4j.Logger;
import transport.HeartbeatHandler;
import transport.PacketCodec;
import transport.ReaderHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 启动Netty
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class Service {

    private static final Logger LOGGER = Logger.getLogger(Service.class);
    private ExecutorService tPool;

    /**
     * 创建线程池，处理非IO操作
     */
    public Service() {
        tPool = Executors.newCachedThreadPool();
        LOGGER.info("线程池配置完成");
    }

    /**
     * 启动服务
     *
     * @param port
     */
    public void start(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("PacketCodec", new PacketCodec());
                            pipeline.addLast("IdleStateHandler", new IdleStateHandler(6, 0, 0));
                            pipeline.addLast("ReaderHandler", new ReaderHandler(tPool));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            LOGGER.info("服务器启动成功");

            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();

            LOGGER.info("服务器关闭");

        } catch (InterruptedException e) {
            LOGGER.warn("Netty", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            tPool.shutdown();
        }
    }
}
