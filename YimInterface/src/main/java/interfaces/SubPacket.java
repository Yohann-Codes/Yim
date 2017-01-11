package interfaces;

import common.CacheVars;
import common.Packet;
import common.PacketType;
import io.netty.util.ReferenceCountUtil;
import packet.ChatPacket;
import packet.ResChatPacket;
import packet.RespLoginPacket;
import packet.RespRegPacket;
import trasport.HeartbeatHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

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

            case PacketType.RESP_CHAT:
                ResChatPacket resChatPacket = (ResChatPacket) packet;
                dealRespChat(resChatPacket);
                break;

            case PacketType.CAHT:
                ChatPacket chatPacket = (ChatPacket) packet;
                dealChat(chatPacket);
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

    /**
     * 聊天响应
     *
     * @param resChatPacket
     */
    public void dealRespChat(ResChatPacket resChatPacket) {
        if (resChatPacket.isSuccessful()) {
            System.out.println("Success");
        } else {
            System.out.println("Default");
        }
        ReferenceCountUtil.release(resChatPacket);
    }

    /**
     * 接收聊天消息
     *
     * @param chatPacket
     */
    public void dealChat(ChatPacket chatPacket) {
        String time = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss").format(new Date(chatPacket.getTime()));
        System.out.println(chatPacket.getSrcUsername() + ": "
                + chatPacket.getMessage() + "   [ " + time + " ]");
        ReferenceCountUtil.release(chatPacket);
    }
}
