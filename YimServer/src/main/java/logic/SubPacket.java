package logic;

import common.Packet;
import common.PacketType;
import io.netty.channel.Channel;
import packet.ChatPacket;
import packet.LoginPacket;
import packet.LogoutPacket;
import packet.RegisterPacket;

/**
 * 判断包类型，分发处理
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class SubPacket implements Runnable {
    private Packet packet;
    private Channel channel;

    public SubPacket(Packet packet, Channel channel) {
        this.packet = packet;
        this.channel = channel;
    }

    public void run() {
        switch (packet.packetType) {
            case PacketType.LOGIN:
                LoginPacket loginPacket = (LoginPacket) packet;
                new LoginLogic(loginPacket, channel).deal();
                break;

            case PacketType.LOGOUT:
                LogoutPacket logoutPacket = (LogoutPacket) packet;
                new LogoutLogic(logoutPacket, channel).deal();
                break;

            case PacketType.REGISTER:
                RegisterPacket registerPacket = (RegisterPacket) packet;
                new RegLogic(registerPacket, channel).deal();
                break;

            case PacketType.CAHT:
                ChatPacket chatPacket = (ChatPacket) packet;
                new ChatLogic(chatPacket, channel).deal();
                break;
        }
    }
}
