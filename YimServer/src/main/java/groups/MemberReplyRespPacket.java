package groups;

import packet.PacketType;
import packet.Response;

/**
 * 邀请答复响应数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class MemberReplyRespPacket extends Response {

    private String groupName;

    public MemberReplyRespPacket(boolean isSuccess,
                                 String hint, int groupId, String groupName) {
        super(isSuccess, hint);
        packetType = PacketType.MEMBER_REPLY_RESP;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
