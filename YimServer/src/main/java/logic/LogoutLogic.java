package logic;

import io.netty.channel.Channel;
import log.MyLog;
import packet.LogoutPacket;

/**
 * Created by yohann on 2017/1/11.
 */
public class LogoutLogic {
    private LogoutPacket logoutPacket;
    private Channel channel;

    public LogoutLogic(LogoutPacket logoutPacket, Channel channel) {
        this.logoutPacket = logoutPacket;
        this.channel = channel;
    }

    public void deal() {
        String username = logoutPacket.getUsername();
        // 关闭连接
        channel.close();
        // 移除连接池中的连接
        ConnPool.remove(username);
        MyLog.userLogger(username + " 退出登录");
    }
}
