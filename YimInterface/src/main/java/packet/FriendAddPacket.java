package packet;

import common.Packet;
import common.PacketType;

/**
 * 添加好友数据包
 *
 * Created by yohann on 2017/1/8.
 */
public class FriendAddPacket extends Packet {

    // 添加者
    private String srcUsername;
    // 被添加者
    private String desUsername;
    // 验证信息
    private String verifyMsg;

    public FriendAddPacket() {
        packetType = PacketType.ADD_FRIEND;
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

    public String getVerifyMsg() {
        return verifyMsg;
    }

    public void setVerifyMsg(String verifyMsg) {
        this.verifyMsg = verifyMsg;
    }
}
