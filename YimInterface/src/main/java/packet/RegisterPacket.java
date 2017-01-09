package packet;

import common.Packet;
import common.PacketType;

/**
 * 注册数据包
 *
 * Created by yohann on 2017/1/8.
 */
public class RegisterPacket extends Packet {

    private String username;
    private String password;

    public RegisterPacket() {
        packetType = PacketType.REGISTER;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
