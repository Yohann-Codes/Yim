package message.group;

import packet.PacketType;
import packet.Response;

/**
 * 讨论组消息响应数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class GroupMsgRespPacket extends Response {
    public GroupMsgRespPacket(boolean isSuccess, String hint) {
        super(isSuccess, hint);
        packetType = PacketType.GROUP_MSG_RESP;
    }
}
