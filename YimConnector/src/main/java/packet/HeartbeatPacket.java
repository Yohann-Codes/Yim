package packet;

/**
 * 心跳包
 * <p>
 * Created by yohann on 2017/1/8.
 */
public class HeartbeatPacket extends Packet {

    private String username;

    public HeartbeatPacket(String username) {
        packetType = PacketType.HEARTBEAT;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
