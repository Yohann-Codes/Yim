package groups;

import common.UserInfo;
import future.Future;

/**
 * 解散讨论组接口（只能被创建讨论组创建者调用）
 *
 * Created by yohann on 2017/1/19.
 */
public class GroupDisband {
    private String username;
    private String groupName;

    public GroupDisband(String username, String groupName) {
        this.username = username;
        this.groupName = groupName;
    }

    public Future execute() {
        GroupDisbandReqPacket groupDisbandReqPacket = new GroupDisbandReqPacket(username, groupName);
        UserInfo.channel.writeAndFlush(groupDisbandReqPacket);
        return Future.getFuture();
    }
}
