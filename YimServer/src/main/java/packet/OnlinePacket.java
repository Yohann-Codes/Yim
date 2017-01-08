package packet;

import common.Packet;
import common.PacketType;

/**
 * 好友上线通知数据包
 *
 * Created by yohann on 2017/1/8.
 */
public class OnlinePacket extends Packet {

    private String username;
    private String time;

    public OnlinePacket() {
        packetType = PacketType.ONLINE;
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
