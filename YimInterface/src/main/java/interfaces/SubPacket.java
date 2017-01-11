package interfaces;

import common.CacheVars;
import common.Packet;
import common.PacketType;
import io.netty.util.ReferenceCountUtil;
import packet.RespLoginPacket;
import packet.RespRegPacket;
import trasport.HeartbeatHandler;

/**
 * 分包处理
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class SubPacket {
    private Packet packet;

    public SubPacket(Packet packet) {
        this.packet = packet;
    }

    public void deal() {
        switch (packet.packetType) {
            case PacketType.RESP_LOGIN:
                RespLoginPacket respLoginPacket = (RespLoginPacket) packet;
                dealRespLogin(respLoginPacket);
                break;

            case PacketType.RESP_REG:
                RespRegPacket respRegPacket = (RespRegPacket) packet;
                dealRespReg(respRegPacket);
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
            // 启动心跳
            CacheVars.channel.pipeline().addAfter("IdleStateHandler", "HeartbeatHandler",
                    new HeartbeatHandler(username));
            System.out.println("Success");
        } else {
            System.out.println("Defeat");
        }
        ReferenceCountUtil.release(respLoginPacket);
    }

    /**
     * 注册响应数据包
     *
     * @param respRegPacket
     */
    public void dealRespReg(RespRegPacket respRegPacket) {
        if (respRegPacket.isSuccessful()) {
            System.out.println("Success");
        } else {
            System.out.println("Default");
        }
        ReferenceCountUtil.release(respRegPacket);
    }
}
