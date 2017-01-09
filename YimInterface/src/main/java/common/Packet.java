package common;

import java.io.Serializable;

/**
 * Created by yohann on 2017/1/8.
 */
public class Packet implements Serializable {

    // 数据包类型
    public int packetType;

    public int getPacketType() {
        return packetType;
    }
}
