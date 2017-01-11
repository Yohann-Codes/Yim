package packet;

import common.Packet;
import common.PacketType;

/**
 * 注册响应数据包
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class RespRegPacket extends Packet {
    private String username;
    private boolean isSuccessful;

    public RespRegPacket() {
        packetType = PacketType.RESP_REG;
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
