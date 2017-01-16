package friends;

import packet.PacketType;
import packet.Request;

/**
 * 好友申请答复请求数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class FriendReplyReqPacket extends Request {

    private String requester;
    private boolean isAgree;

    public FriendReplyReqPacket(String username, String requester, boolean isAgree) {
        super(username);
        packetType = PacketType.FRIEND_REPLY_REQ;
        this.requester = requester;
        this.isAgree = isAgree;
    }

    public String getRequester() {
        return requester;
    }

    public boolean isAgree() {
        return isAgree;
    }
}
