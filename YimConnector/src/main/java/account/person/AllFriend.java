package account.person;

import common.UserInfo;
import future.Future;

/**
 * 查询已添加的好友接口
 *
 * 如果有添加的好友，回调 onExist(Map<String, Boolean> friends)
 *                  参数friends：key->好友用户名，value->是否在线(true/false->在线/离线)
 * 如果没有添加好友，回调 onNoExist()
 *
 * Future future = new AllFriend(username).execute();
 * future.addListener(new AllFriendFutureListener() {
 *     public void onExist(Map<String, Boolean> friends) {
 *         Set<Map.Entry<String, Boolean>> entries = friends.entrySet();
 *         Iterator<Map.Entry<String, Boolean>> ite = entries.iterator();
 *         while (ite.hasNext()) {
 *             Map.Entry<String, Boolean> entry = ite.next();
 *             String username = entry.getKey();
 *             Boolean isOnline = entry.getValue();
 *             if (isOnline) {
 *                 System.out.println(username + "  在线");
 *             } else {
 *                 System.out.println(username + "  离线");
 *             }
 *         }
 *     }
 *
 *     public void onNoExist() {
 *         System.out.println("没有添加好友");
 *     }
 * });
 *
 * Created by yohann on 2017/1/18.
 */
public class AllFriend {
    private String username;

    public AllFriend(String username) {
        this.username = username;
    }

    public Future execute() {
        AllFriendReqPacket allFriendReqPacket = new AllFriendReqPacket(username);
        UserInfo.channel.writeAndFlush(allFriendReqPacket);
        return Future.getFuture();
    }
}
