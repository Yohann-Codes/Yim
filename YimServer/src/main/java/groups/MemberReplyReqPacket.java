package groups;

import packet.PacketType;
import packet.Request;

/**
 * 邀请答复请求数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class MemberReplyReqPacket extends Request {

    private String groupName;
    private boolean isAgree;

    public MemberReplyReqPacket(String username, String groupName, boolean isAgree) {
        super(username);
        packetType = PacketType.MEMBER_REPLY_REQ;
        this.groupName = groupName;
        this.isAgree = isAgree;
    }

    public String getGroupName() {
        return groupName;
    }

    public boolean isAgree() {
        return isAgree;
    }
}
