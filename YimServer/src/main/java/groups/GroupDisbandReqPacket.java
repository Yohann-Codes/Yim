package groups;

import packet.PacketType;
import packet.Request;

/**
 * 解散讨论组请求数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class GroupDisbandReqPacket extends Request {

    private String groupName;

    public GroupDisbandReqPacket(String username, String groupName) {
        super(username);
        packetType = PacketType.GROUP_DISBAND_REQ;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
