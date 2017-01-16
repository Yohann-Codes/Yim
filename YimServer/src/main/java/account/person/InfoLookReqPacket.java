package account.person;

import packet.PacketType;
import packet.Request;

/**
 * 查看个人信息请求数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class InfoLookReqPacket extends Request {
    public InfoLookReqPacket(String username) {
        super(username);
        packetType = PacketType.INFO_LOOK_REQ;
    }
}
