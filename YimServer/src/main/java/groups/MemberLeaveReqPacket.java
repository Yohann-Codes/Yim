package groups;

import packet.PacketType;
import packet.Request;

/**
 * 退出讨论组请求数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class MemberLeaveReqPacket extends Request {

    private String groupName;

    public MemberLeaveReqPacket(String username, String groupName) {
        super(username);
        packetType = PacketType.MEMBER_LEAVE_REQ;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
