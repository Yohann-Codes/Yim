package notice;

import packet.Packet;
import packet.PacketType;

/**
 * 服务器给客户端发送通知的数据包
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class Notice extends Packet {
    protected String content;

    protected Notice(String content) {
        packetType = PacketType.NOTICE;
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
