package account.login;

import packet.PacketType;
import packet.Response;

/**
 * 登录响应数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class LoginRespPacket extends Response {

    public String username;
    // 用于断线重连的验证信息
    public Long token;

    public LoginRespPacket(String username, boolean isSuccess, Long token, String hint) {
        super(isSuccess, hint);
        packetType = PacketType.LOGIN_RESP;
        this.token = token;
        this.username = username;
    }

    public Long getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }
}
