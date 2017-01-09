package interfaces;

import common.Packet;
import common.PacketType;

/**
 * Created by yohann on 2017/1/9.
 */
public class SubPacket {
    private Packet packet;
    private Connection conn;

    public SubPacket(Packet packet, Connection conn) {
        this.packet = packet;
        this.conn = conn;
    }

    public void deal() {
        switch (packet.packetType) {
            case PacketType.RESPONSE:
                conn.onFinishLogin();
                break;
        }
    }
}
