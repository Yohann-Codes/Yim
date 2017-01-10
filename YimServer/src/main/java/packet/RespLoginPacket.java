package packet;

import common.Packet;
import common.PacketType;

/**
 * 登录响应数据包
 *
 * Created by yohann on 2017/1/9.
 */
public class RespLoginPacket extends Packet {
    private String username;
    private boolean isSuccessful;

    public RespLoginPacket() {
        packetType = PacketType.RESP_LOGIN;
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
