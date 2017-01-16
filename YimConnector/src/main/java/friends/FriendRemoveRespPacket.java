package friends;

import packet.PacketType;
import packet.Response;

/**
 * 删除好友响应数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class FriendRemoveRespPacket extends Response {

    private String friend;

    public FriendRemoveRespPacket(boolean isSuccess, String hint, String friend) {
        super(isSuccess, hint);
        packetType = PacketType.FRIEND_REMOVE_RESP;
        this.friend = friend;
    }

    public String getFriend() {
        return friend;
    }
}
