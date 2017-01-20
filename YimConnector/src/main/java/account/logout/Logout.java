package account.logout;

import common.UserInfo;

/**
 * 登录接口
 *
 * new Logout().execute();
 *
 * Created by yohann on 2017/1/16.
 */
public class Logout {

    public Logout() {
        UserInfo.isLogout = true;
    }

    public void execute() {
        LogoutReqPacket logoutReqPacket =
                new LogoutReqPacket(UserInfo.username, UserInfo.token);
        UserInfo.channel.writeAndFlush(logoutReqPacket);
    }
}
