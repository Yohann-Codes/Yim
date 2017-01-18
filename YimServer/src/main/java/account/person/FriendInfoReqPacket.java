package account.person;

import packet.PacketType;
import packet.Request;

/**
 * 查看好友详细信息请求数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class FriendInfoReqPacket extends Request {

    private String friend;

    public FriendInfoReqPacket(String username, String friend) {
        super(username);
        packetType = PacketType.FRIEND_INFO_REQ;
        this.friend = friend;
    }

    public String getFriend() {
        return friend;
    }
}
