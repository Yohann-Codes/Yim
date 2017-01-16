package groups;

import packet.PacketType;
import packet.Response;

/**
 * 创建讨论组响应数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class GroupCreateRespPacket extends Response {

    private String groupName;

    public GroupCreateRespPacket(boolean isSuccess,
                                 String groupName, String hint) {
        super(isSuccess, hint);
        packetType = PacketType.GROUP_CREATE_RESP;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
