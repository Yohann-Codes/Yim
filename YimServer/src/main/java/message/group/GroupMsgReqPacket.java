package message.group;

import message.Message;
import packet.PacketType;

/**
 * 讨论组消息请求数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class GroupMsgReqPacket extends Message {
    private String groupName;

    public GroupMsgReqPacket(String username, String groupName, String message, long time) {
        super(username, message, time);
        packetType = PacketType.GROUP_MSG_REQ;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
