package friends;

import packet.PacketType;
import packet.Request;

/**
 * 添加好友请求数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class FriendAddReqPacket extends Request {

    private String responser;
    private String info;

    public FriendAddReqPacket(String username, String responser, String info) {
        super(username);
        packetType = PacketType.FRIEND_ADD_REQ;
        this.responser = responser;
        this.info = info;
    }

    public String getResponser() {
        return responser;
    }

    public String getInfo() {
        return info;
    }
}
