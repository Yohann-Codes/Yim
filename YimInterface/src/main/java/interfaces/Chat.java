package interfaces;

import common.CacheVars;
import packet.ChatPacket;

/**
 * 聊天接口
 * <p>
 * Created by yohann on 2017/1/11.
 */
public class Chat {
    private String desUsername;
    private String msg;

    public Chat(String desUsername, String msg) {
        this.desUsername = desUsername;
        this.msg = msg;
    }

    public void execute() {
        ChatPacket chatPacket = new ChatPacket();
        chatPacket.setSrcUsername(CacheVars.username);
        chatPacket.setDesUsername(desUsername);
        chatPacket.setMessage(msg);
        chatPacket.setTime(System.currentTimeMillis());
        CacheVars.channel.writeAndFlush(chatPacket);
    }
}
