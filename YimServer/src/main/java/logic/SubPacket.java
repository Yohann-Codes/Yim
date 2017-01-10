package logic;

import common.Packet;
import common.PacketType;
import io.netty.channel.ChannelHandlerContext;
import packet.LoginPacket;

/**
 * 判断包类型，分发处理
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class SubPacket implements Runnable {
    private Packet packet;
    private ChannelHandlerContext ctx;

    public SubPacket(Packet packet, ChannelHandlerContext ctx) {
        this.packet = packet;
        this.ctx = ctx;
    }

    public void run() {
        switch (packet.packetType) {
            case PacketType.LOGIN:
                LoginPacket loginPacket = (LoginPacket) packet;
                new LoginLogic(loginPacket, ctx).deal();
                break;
        }
    }
}
