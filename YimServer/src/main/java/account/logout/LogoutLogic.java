package account.logout;

import connection.ConnPool;
import connection.TokenPool;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

/**
 * 登出逻辑
 * <p>
 * Created by yohann on 2017/1/16.
 */
public class LogoutLogic {
    private static final Logger LOGGER = Logger.getLogger(LogoutLogic.class);

    private LogoutReqPacket logoutReqPacket;
    private Channel channel;

    public LogoutLogic(LogoutReqPacket logoutReqPacket, Channel channel) {
        this.logoutReqPacket = logoutReqPacket;
        this.channel = channel;
    }

    public void deal() {
        String username = logoutReqPacket.getUsername();
        // 移除维护的连接和token
        TokenPool.remove(logoutReqPacket.getToken());
        ConnPool.remove(username);

        // 关闭channel
        try {
            channel.close().sync();
        } catch (InterruptedException e) {
            LOGGER.warn("关闭channel异常", e);
        }

        LOGGER.info(username + " 退出登录");
    }
}
