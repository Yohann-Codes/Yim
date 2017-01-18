package friends;

import common.UserInfo;
import future.Future;

/**
 * 好友申请回复接口
 *
 * Future future = new FriendReply(username, requester, true/false).execute();
 * future.addListener(new FriendReplyFutureListener() {
 *     public void onSuccess() {
 *         System.out.println("发送成功");
 *     }
 *
 *     public void onFailure(String hint) {
 *         System.out.println("发送失败，错误提示：" + hint);
 *     }
 * });
 *
 * Created by yohann on 2017/1/18.
 */
public class FriendReply {
    private String username;
    private String requester;
    private boolean isAgree;

    public FriendReply(String username, String requester, boolean isAgree) {
        this.username = username;
        this.requester = requester;
        this.isAgree = isAgree;
    }

    public Future execute() {
        FriendReplyReqPacket friendReplyReqPacket = new FriendReplyReqPacket(username, requester, isAgree);
        UserInfo.channel.writeAndFlush(friendReplyReqPacket);
        return Future.getFuture();
    }
}
