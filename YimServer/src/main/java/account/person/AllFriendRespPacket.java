package account.person;

import packet.PacketType;
import packet.Response;

import java.util.Map;

/**
 * 查看我的好友响应数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class AllFriendRespPacket extends Response {

    // key->好友用户名，value->是否在线
    private Map<String, Boolean> friendMap;

    public AllFriendRespPacket(boolean isSuccess,
                               String hint, Map<String, Boolean> friendMap) {
        super(isSuccess, hint);
        packetType = PacketType.ALL_FRIEND_RESP;
        this.friendMap = friendMap;
    }

    public Map<String, Boolean> getFriendMap() {
        return friendMap;
    }
}
