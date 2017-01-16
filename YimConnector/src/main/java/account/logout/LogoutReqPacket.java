package account.logout;

import packet.PacketType;
import packet.Request;

/**
 * 登出请求数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class LogoutReqPacket extends Request {
    private Long token;

    public LogoutReqPacket(String username, Long token) {
        super(username);
        packetType = PacketType.LOGOUT_REQ;
        this.token = token;
    }

    public Long getToken() {
        return token;
    }
}
