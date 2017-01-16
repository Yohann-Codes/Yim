package account.register;

import packet.PacketType;
import packet.Request;

/**
 * 注册请求数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class RegReqPacket extends Request {

    private String password;

    public RegReqPacket(String username, String password) {
        super(username);
        packetType = PacketType.REGISTER_REQ;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
