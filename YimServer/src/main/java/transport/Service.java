package transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 网络数据传输服务
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class Service {
    private ExecutorService tPool;

    /**
     * 创建线程池，处理非IO操作
     *
     * @param nThreads
     */
    public Service(int nThreads) {
        tPool = Executors.newFixedThreadPool(nThreads);
        System.out.println("-- 线程池配置完成 --");
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
                            pipeline.addLast(new IdleStateHandler(6, 0, 0));
                            pipeline.addLast("HeartbeatHandler", new HeartbeatHandler());
                            pipeline.addLast("ReaderHandler", new ReaderHandler(tPool));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("-- 服务器启动成功 --");

            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            tPool.shutdown();
        }
    }
}
