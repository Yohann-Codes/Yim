package groups;

import packet.PacketType;
import packet.Response;

/**
 * 退出讨论组响应数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class MemberLeaveRespPacket extends Response {

    private String groupName;
    private String member;

    public MemberLeaveRespPacket(boolean isSuccess,
                                 String hint, String groupName, String member) {
        super(isSuccess, hint);
        packetType = PacketType.MEMBER_LEAVE_RESP;
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
