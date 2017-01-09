package packet;

import common.Packet;
import common.PacketType;

/**
 * Created by yohann on 2017/1/8.
 */
public class OfflinePacket extends Packet {

    private String username;
    private String time;

    public OfflinePacket() {
        packetType = PacketType.OFFLINE;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
