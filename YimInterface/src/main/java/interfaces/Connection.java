package interfaces;

import io.netty.channel.ChannelHandlerContext;
import trasport.Service;

/**
 * 所有与服务器交互类的基类
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class Connection {
    public Service service;

    /**
     * 连接服务器
     *
     * @param host
     * @param port
     */
    public Connection(String host, int port) {
        service = new Service(this);
        service.connect(host, port);
    }

    /**
     * 连接成功
     *
     * @param ctx
     */
    public void onFinishConnect(ChannelHandlerContext ctx) {
    }

    /**
     * 登录操作完成，服务器返回响应数据包
     *
     * @param
     */
    public void onFinishLogin() {
    }
}
