package groups;

import packet.PacketType;
import packet.Request;

/**
 * 邀请成员请求数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class MemberInviteReqPacket extends Request {

    private String responser;
    private String groupName;

    public MemberInviteReqPacket(String username, String groupName, String responser) {
        super(username);
        packetType = PacketType.MEMBER_INVITE_REQ;
        this.groupName = groupName;
        this.responser = responser;
    }

    public String getResponser() {
        return responser;
    }

    public String getGroupName() {
        return groupName;
    }
}
