package account.register;

import packet.PacketType;
import packet.Response;

/**
 * 注册响应数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class RegRespPacket extends Response {

    public RegRespPacket(boolean isSuccess, String hint) {
        super(isSuccess, hint);
        packetType = PacketType.REGISTER_RESP;
    }
}
