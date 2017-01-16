package groups;

import packet.PacketType;
import packet.Request;

/**
 * 踢出成员请求数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class MemberKickReqPacket extends Request {

    private String groupName;
    private String member;

    public MemberKickReqPacket(String username, String groupName, String member) {
        super(username);
        packetType = PacketType.MEMBER_KICK_REQ;
        this.groupName = groupName;
        this.member = member;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getMember() {
        return member;
    }
}
