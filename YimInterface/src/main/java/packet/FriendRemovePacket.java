package packet;

import common.Packet;
import common.PacketType;

/**
 * 删除好友数据包
 *
 * Created by yohann on 2017/1/8.
 */
public class FriendRemovePacket extends Packet {

    private String srcUsername;
    private String desUsername;

    public FriendRemovePacket() {
        packetType = PacketType.REMOVE_FRIEND;
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
