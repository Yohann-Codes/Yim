package friends;

import common.UserInfo;
import future.Future;

/**
 * 删除好友接口
 *
 * Future future = new FriendRemove(username, friend).execute();
 * future.addListener(new FriendRemoveFutureListener() {
 *     public void onSuccess() {
 *         System.out.println("删除好友成功");
 *     }
 *
 *     public void onFailure(String hint) {
 *         System.out.println("删除好友失败，错误提示：" + hint);
 *     }
 * });
 *
 * Created by yohann on 2017/1/18.
 */
public class FriendRemove {
    private String username;
    private String friend;

    public FriendRemove(String username, String friend) {
        this.username = username;
        this.friend = friend;
    }

    public Future execute() {
        FriendRemoveReqPacket friendRemoveReqPacket = new FriendRemoveReqPacket(username, friend);
        UserInfo.channel.writeAndFlush(friendRemoveReqPacket);
        return Future.getFuture();
    }
}
