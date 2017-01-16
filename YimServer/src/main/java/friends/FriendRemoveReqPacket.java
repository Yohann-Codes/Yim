package friends;

import packet.PacketType;
import packet.Request;

/**
 * 删除好友请求数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class FriendRemoveReqPacket extends Request {

    private String friend;

    public FriendRemoveReqPacket(String username, String friend) {
        super(username);
        packetType = PacketType.FRIEND_REMOVE_REQ;
        this.friend = friend;
    }

    public String getFriend() {
        return friend;
    }
}
