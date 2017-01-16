package groups;

import packet.PacketType;
import packet.Response;

/**
 * 踢出成员响应数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class MemberKickRespPacket extends Response {

    private String groupName;
    private String member;

    public MemberKickRespPacket(boolean isSuccess, String hint,
                                String groupName, String member) {
        super(isSuccess, hint);
        packetType = PacketType.MEMBER_KICK_RESP;
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
