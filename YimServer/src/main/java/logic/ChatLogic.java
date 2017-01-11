package logic;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.ReferenceCountUtil;
import log.MyLog;
import packet.ChatPacket;
import packet.ResChatPacket;

/**
 * 聊天数据处理
 * <p>
 * Created by yohann on 2017/1/11.
 */
public class ChatLogic {
    private ChatPacket chatPacket;
    private Channel srcChannel;

    public ChatLogic(ChatPacket chatPacket, Channel channel) {
        this.chatPacket = chatPacket;
        srcChannel = channel;
    }

    public void deal() {
        String srcUsername = chatPacket.getSrcUsername();
        String desUsername = chatPacket.getDesUsername();
        String message = chatPacket.getMessage();
        long time = chatPacket.getTime();

        // 在Online用户中查找，ConnPool
        Channel desChannel = ConnPool.query(desUsername);
        if (desChannel != null) {
            online(desChannel, srcUsername, desUsername);
        } else {
            // 在Offline用户中查找，数据库
            offline(srcUsername, desUsername, message, time);
        }
    }

    /**
     * 在线消息，直接发送给用户
     *
     * @param channel
     * @param srcUsername
     * @param desUsername @return
     */
    public void online(Channel channel, final String srcUsername, final String desUsername) {
        ChannelFuture future = channel.writeAndFlush(chatPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    MyLog.sysLogger("在线消息 " + srcUsername + "-->" + desUsername + " 转发成功");
                    response(srcUsername, true);
                } else {
                    MyLog.sysLogger("在线消息 " + srcUsername + "-->" + desUsername + " 转发失败");
                    response(srcUsername, false);
                }
            }
        });
    }

    /**
     * 离线消息，先存入数据库，待用户上线时发送
     *
     * @param srcUsername
     * @param desUsername
     * @param message
     * @param time
     * @return
     */
    public void offline(String srcUsername,
                        String desUsername, String message, long time) {

    }

    /**
     * 响应
     *
     * @param result
     */
    public void response(String username, boolean result) {
        ResChatPacket resChatPacket = new ResChatPacket();
        resChatPacket.setUsername(username);
        resChatPacket.setSuccessful(result);
        ChannelFuture future = srcChannel.writeAndFlush(resChatPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                ReferenceCountUtil.release(chatPacket);
            }
        });
    }
}
