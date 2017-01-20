package message.group;

import common.UserInfo;
import future.Future;

/**
 * 讨论组消息接口
 *
 * future = new GroupMsg(groupName, message).execute();
 * future.addListener(new GroupMsgFutureListener() {
 *     public void onSuccess() {
 *         System.out.println("发送成功");
 *     }
 *
 *     public void onFailure(String hint) {
 *         System.out.println("发送失败，错误提示：" + hint);
 *     }
 * });
 *
 * Created by yohann on 2017/1/20.
 */
public class GroupMsg {
    private String groupName;
    private String message;

    public GroupMsg(String groupName, String message) {
        this.groupName = groupName;
        this.message = message;
    }

    public Future execute() {
        GroupMsgReqPacket groupMsgReqPacket =
                new GroupMsgReqPacket(UserInfo.username, groupName, message, System.currentTimeMillis());
        UserInfo.channel.writeAndFlush(groupMsgReqPacket);
        return Future.getFuture();
    }
}
