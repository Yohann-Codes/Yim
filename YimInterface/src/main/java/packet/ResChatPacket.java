package packet;

import common.Packet;
import common.PacketType;

/**
 * 聊天响应数据包
 * <p>
 * Created by yohann on 2017/1/11.
 */
public class ResChatPacket extends Packet {
    private String username;
    private boolean isSuccessful;

    public ResChatPacket() {
        packetType = PacketType.RESP_CHAT;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
}
