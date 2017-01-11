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
     * 连接成功
     */
    public void onFinishConnect() {
    }
}
