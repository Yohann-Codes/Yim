package groups;

import common.UserInfo;
import future.Future;

/**
 * 踢出成员接口（只能由创建者执行）
 *
 * Future future = new MemberKick(username, groupName, member).execute();
 * future.addListener(new MemberKickFutureListener() {
 *     public void onSuccess(String groupName, String member) {
 *         System.out.println(member + " 已被踢出讨论组<" + groupName + ">");
 *     }
 *
 *     public void onFailure(String hint) {
 *         System.out.println("踢出成员失败，错误提示：" + hint);
 *     }
 * });
 *
 * Created by yohann on 2017/1/19.
 */
public class MemberKick {
    private String username;
    private String groupName;
    private String member;

    public MemberKick(String username, String groupName, String member) {
        this.username = username;
        this.groupName = groupName;
        this.member = member;
    }

    public Future execute() {
        MemberKickReqPacket memberKickReqPacket = new MemberKickReqPacket(username, groupName, member);
        UserInfo.channel.writeAndFlush(memberKickReqPacket);
        return Future.getFuture();
    }
}
