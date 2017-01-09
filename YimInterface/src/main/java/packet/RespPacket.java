package packet;

import common.Packet;
import common.PacketType;

/**
 * Created by yohann on 2017/1/9.
 */
public class RespPacket extends Packet {
    private boolean isSuccessful;

    public RespPacket() {
        packetType = PacketType.RESPONSE;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
}
