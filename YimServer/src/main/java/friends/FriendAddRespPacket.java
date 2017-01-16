package friends;

import packet.PacketType;
import packet.Response;

/**
 * 添加好友响应数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class FriendAddRespPacket extends Response {
    public FriendAddRespPacket(boolean isSuccess, String hint) {
        super(isSuccess, hint);
        packetType = PacketType.FRIEND_ADD_RESP;
    }
}
