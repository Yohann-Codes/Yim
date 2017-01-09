package packet;

import common.Packet;
import common.PacketType;

/**
 * 聊天数据包
 *
 * Created by yohann on 2017/1/8.
 */
public class ChatPacket extends Packet {

    private String srcUsername;
    private String desUsername;
    private String message;
    private long time;

    public ChatPacket() {
        packetType = PacketType.CAHT;
    }

    public String getSrcUsername() {
        return srcUsername;
    }

    public void setSrcUsername(String srcUsername) {
        this.srcUsername = srcUsername;
    }

    public String getDesUsername() {
        return desUsername;
    }

    public void setDesUsername(String desUsername) {
        this.desUsername = desUsername;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ChatPacket{" +
                "srcUsername='" + srcUsername + '\'' +
                ", desUsername='" + desUsername + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}