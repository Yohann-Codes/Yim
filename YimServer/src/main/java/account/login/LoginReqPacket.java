package account.login;

import packet.PacketType;
import packet.Request;

/**
 * 登录请求数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class LoginReqPacket extends Request {

    private String password;

    public LoginReqPacket(String username, String password) {
        super(username);
        packetType = PacketType.LOGIN_REQ;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
