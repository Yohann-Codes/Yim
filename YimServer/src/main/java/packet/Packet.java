package packet;

import java.io.Serializable;

/**
 * 所有的数据包都继承此类
 * <p>
 * Created by yohann on 2017/1/8.
 */
public class Packet implements Serializable {

    // 数据包类型
    protected int packetType;

    public int getPacketType() {
        return packetType;
    }
}
