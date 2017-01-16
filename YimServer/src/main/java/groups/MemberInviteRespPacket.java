package groups;

import packet.PacketType;
import packet.Response;

/**
 * 邀请成员响应数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class MemberInviteRespPacket extends Response {

    public MemberInviteRespPacket(boolean isSuccess, String hint) {
        super(isSuccess, hint);
        packetType = PacketType.MEMBER_INVITE_RESP;
    }
}
