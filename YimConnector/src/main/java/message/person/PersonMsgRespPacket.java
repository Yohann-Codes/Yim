package message.person;

import packet.PacketType;
import packet.Response;

/**
 * 个人消息响应数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class PersonMsgRespPacket extends Response {
    public PersonMsgRespPacket(boolean isSuccess, String hint) {
        super(isSuccess, hint);
        packetType = PacketType.PERSON_MSG_RESP;
    }
}
