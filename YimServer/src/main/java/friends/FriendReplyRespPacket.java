package friends;

import packet.PacketType;
import packet.Response;

/**
 * 好友申请答复响应包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class FriendReplyRespPacket extends Response {
    public FriendReplyRespPacket(boolean isSuccess, String hint) {
        super(isSuccess, hint);
        packetType = PacketType.FRIEND_REPLY_RESP;
    }
}
