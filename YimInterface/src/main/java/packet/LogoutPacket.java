package packet;

import common.Packet;
import common.PacketType;

/**
 * 登出数据包
 *
 * Created by yohann on 2017/1/8.
 */
public class LogoutPacket extends Packet {
    private String username;

    public LogoutPacket() {
        packetType = PacketType.LOGOUT;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
