package groups;

import packet.PacketType;
import packet.Response;

/**
 * 解散讨论组响应数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class GroupDisbandRespPacket extends Response {

    private String groupName;

    public GroupDisbandRespPacket(boolean isSuccess, String hint, String groupName) {
        super(isSuccess, hint);
        packetType = PacketType.GROUP_DISBAND_RESP;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
