package packet;

import common.Packet;
import common.PacketType;

/**
 * 心跳包
 *
 * Created by yohann on 2017/1/8.
 */
public class HeartbeatPacket extends Packet {

    private String username;

    public HeartbeatPacket() {
        packetType = PacketType.HEARTBEAT;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
