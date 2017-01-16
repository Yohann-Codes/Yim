package account.person;

import packet.PacketType;
import packet.Request;

/**
 * 查看我的好友请求数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class AllFriendReqPacket extends Request {
    public AllFriendReqPacket(String username) {
        super(username);
        packetType = PacketType.ALL_FRIEND_REQ;
    }
}
