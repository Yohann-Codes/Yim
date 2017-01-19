package groups;

import common.UserInfo;
import future.Future;

/**
 * 查看所在全部讨论组及讨论组的成员接口
 *
 * Future future = new AllGroups(username).execute();
 * future.addListener(new AllGroupsFutureListener() {
 *     public void onReceiveAllGroups(Map<String, List<String>> groups) {
 *         if (groups.size() != 0) {
 *             Set<Map.Entry<String, List<String>>> entries = groups.entrySet();
 *             Iterator<Map.Entry<String, List<String>>> ite = entries.iterator();
 *             while (ite.hasNext()) {
 *                 Map.Entry<String, List<String>> group = ite.next();
 *                 String groupName = group.getKey();
 *                 List<String> members = group.getValue();
 *                 System.out.println(groupName + " " + members);
 *             }
 *         } else {
 *             System.out.println("没有加入任何讨论组");
 *         }
 *     }
 * });
 *
 * Created by yohann on 2017/1/19.
 */
public class AllGroups {
    private String username;

    public AllGroups(String username) {
        this.username = username;
    }

    public Future execute() {
        AllGroupsReqPacket allGroupsReqPacket = new AllGroupsReqPacket(username);
        UserInfo.channel.writeAndFlush(allGroupsReqPacket);
        return Future.getFuture();
    }
}