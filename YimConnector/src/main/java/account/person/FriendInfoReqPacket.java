package account.person;

import packet.PacketType;
import packet.Request;

/**
 * 查看好友详细信息请求数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class FriendInfoReqPacket extends Request {
    public FriendInfoReqPacket(String username) {
        super(username);
        packetType = PacketType.FRIEND_INFO_REQ;
    }
}
