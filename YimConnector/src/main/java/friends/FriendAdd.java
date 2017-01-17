package friends;

import common.UserInfo;
import future.Future;

/**
 * 申请添加好友接口
 *
 * Future future = new FriendAdd(username, responser, info).execute();
 * future.addListener(new FriendAddFutureListener() {
 *     public void onSuccess() {
 *         System.out.println("发送成功，等待对方处理");
 *     }
 *
 *     public void onFailure(String hint) {
 *         System.out.println("发送失败，错误提示：" + hint);
 *     }
 * });
 *
 * Created by yohann on 2017/1/17.
 */
public class FriendAdd {
    private String requester;
    private String responser;
    private String info;

    public FriendAdd(String requester, String responser, String info) {
        this.requester = requester;
        this.responser = responser;
        this.info = info;
    }

    public Future execute() {
        FriendAddReqPacket friendAddReqPacket = new FriendAddReqPacket(requester, responser, info);
        UserInfo.channel.writeAndFlush(friendAddReqPacket);
        return Future.getFuture();
    }
}
