package message.person;

import message.Message;
import packet.PacketType;

/**
 * 个人消息请求数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class PersonMsgReqPacket extends Message {

    private String receiver;

    public PersonMsgReqPacket(String username, String receiver, String message, long time) {
        super(username, message, time);
        packetType = PacketType.PERSON_MSG_REQ;
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }
}
