package account.person;

import packet.PacketType;
import packet.Response;

/**
 * 修改个人详细信息响应数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class InfoUpdateRespPacket extends Response {
    public InfoUpdateRespPacket(boolean isSuccess, String hint) {
        super(isSuccess, hint);
        packetType = PacketType.INFO_UPDATE_RESP;
    }
}
