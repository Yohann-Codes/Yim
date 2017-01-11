package interfaces;

import common.CacheVars;
import packet.LogoutPacket;

/**
 * 登出
 * <p>
 * Created by yohann on 2017/1/10.
 */
public class Logout {
    public void execute() {
        LogoutPacket logoutPacket = new LogoutPacket();
        logoutPacket.setUsername(CacheVars.username);
        CacheVars.channel.writeAndFlush(logoutPacket);
    }
}
