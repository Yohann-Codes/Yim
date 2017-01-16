package groups;

import packet.PacketType;
import packet.Request;

/**
 * 创建讨论组请求数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class GroupCreateReqPacket extends Request {

    private String groupName;

    public GroupCreateReqPacket(String username, String groupName) {
        super(username);
        packetType = PacketType.GROUP_CREATE_REQ;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
