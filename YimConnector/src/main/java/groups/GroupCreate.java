package groups;

import common.UserInfo;
import future.Future;

/**
 * 创建讨论组接口
 *
 * Future future = new GroupCreate(username, groupName).execute();
 * future.addListener(new GroupCreateFutureListener() {
 *     public void onSuccess(String groupName) {
 *         System.out.println("讨论组<" + groupName + "> 创建成功");
 *     }
 *
 *     public void onFailure(String hint) {
 *         System.out.println("讨论组创建失败，错误提示：" + hint);
 *     }
 * });
 *
 * Created by yohann on 2017/1/19.
 */
public class GroupCreate {
    private String username;
    private String groupName;

    public GroupCreate(String username, String groupName) {
        this.username = username;
        this.groupName = groupName;
    }

    public Future execute() {
        GroupCreateReqPacket groupCreateReqPacket = new GroupCreateReqPacket(username, groupName);
        UserInfo.channel.writeAndFlush(groupCreateReqPacket);
        return Future.getFuture();
    }
}
