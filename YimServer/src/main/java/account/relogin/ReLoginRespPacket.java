package account.relogin;

import packet.PacketType;
import packet.Response;

/**
 * 重连响应数据包
 *
 * Created by yohann on 2017/1/20.
 */
public class ReLoginRespPacket extends Response {
    protected ReLoginRespPacket(boolean isSuccess, String hint) {
        super(isSuccess, hint);
        packetType = PacketType.RE_LOGIN_RESP;
    }
}
