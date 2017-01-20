package account.login;

import packet.PacketType;
import packet.Request;

/**
 * 重连请求数据包
 *
 * Created by yohann on 2017/1/20.
 */
public class ReLoginReqPacket extends Request{
    private Long token;

    protected ReLoginReqPacket(String username, Long token) {
        super(username);
        packetType = PacketType.RE_LOGIN_REQ;
        this.token = token;
    }

    public Long getToken() {
        return token;
    }
}
