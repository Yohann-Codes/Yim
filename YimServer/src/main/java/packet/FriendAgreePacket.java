package packet;

import common.Packet;
import common.PacketType;

/**
 * 同意添加好友数据包
 *
 * Created by yohann on 2017/1/8.
 */
public class FriendAgreePacket extends Packet {

    private String srcUsername;
    private String desUsername;

    public FriendAgreePacket() {
        packetType = PacketType.AGREE_FRIEND;
    }

    public String getSrcUsername() {
        return srcUsername;
    }

    public void setSrcUsername(String srcUsername) {
        this.srcUsername = srcUsername;
    }

    public String getDesUsername() {
        return desUsername;
    }

    public void setDesUsername(String desUsername) {
        this.desUsername = desUsername;
    }
}
