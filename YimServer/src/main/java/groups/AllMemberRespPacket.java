package groups;

import packet.PacketType;
import packet.Response;

/**
 * 查看讨论组所有成员响应数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class AllMemberRespPacket extends Response {

    private String groupName;
    private String[] members;

    public AllMemberRespPacket(boolean isSuccess, String hint,
                               String groupName, String[] members) {
        super(isSuccess, hint);
        packetType = PacketType.ALL_MEMBER_RESP;
        this.groupName = groupName;
        this.members = members;
    }

    public String getGroupName() {
        return groupName;
    }

    public String[] getMembers() {
        return members;
    }
}
