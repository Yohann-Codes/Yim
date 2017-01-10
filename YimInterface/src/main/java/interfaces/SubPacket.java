package interfaces;

import common.CacheVars;
import common.Packet;
import common.PacketType;
import io.netty.channel.ChannelHandlerContext;
import packet.RespLoginPacket;
import trasport.HeartbeatHandler;

/**
 * 分包处理
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class SubPacket {
    private Packet packet;
    private ChannelHandlerContext ctx;

    public SubPacket(Packet packet, ChannelHandlerContext ctx) {
        this.packet = packet;
        this.ctx = ctx;
    }

    public void deal() {
        switch (packet.packetType) {
            case PacketType.RESP_LOGIN:
                RespLoginPacket respLoginPacket = (RespLoginPacket) packet;
                dealRespLogin(respLoginPacket);
                break;
        }
    }

    /**
     * 登录响应数据包
     *
     * @param respLoginPacket
     */
    public void dealRespLogin(RespLoginPacket respLoginPacket) {
        if (respLoginPacket.isSuccessful()) {
            String username = respLoginPacket.getUsername();
            CacheVars.username = username;
            CacheVars.ctx = ctx;
            // 启动心跳
            ctx.pipeline().addAfter("IdleStateHandler", "HeartbeatHandler",
                    new HeartbeatHandler(username));
            System.out.println("Success");
        } else {
            System.out.println("Defeat");
        }
    }
}
