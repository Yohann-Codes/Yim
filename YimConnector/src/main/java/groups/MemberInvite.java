package groups;

import common.UserInfo;
import future.Future;

/**
 * 讨论组邀请成员接口（只能由创建者执行）
 *
 * Future future = new MemberInvite(username, groupName, responser).execute();
 * future.addListener(new MemberInviteFutureListener() {
 *     public void onSuccess() {
 *         System.out.println("邀请成功");
 *     }
 *
 *     public void onFailure(String hint) {
 *         System.out.println("邀请失败，错误提示：" + hint);
 *     }
 * });
 *
 * Created by yohann on 2017/1/19.
 */
public class MemberInvite {
    private String username;
    private String groupName;
    private String repsoner;

    public MemberInvite(String username, String groupName, String repsoner) {
        this.username = username;
        this.repsoner = repsoner;
        this.groupName = groupName;
    }

    public Future execute() {
        MemberInviteReqPacket memberInviteReqPacket = new MemberInviteReqPacket(username, groupName, repsoner);
        UserInfo.channel.writeAndFlush(memberInviteReqPacket);
        return Future.getFuture();
    }
}
