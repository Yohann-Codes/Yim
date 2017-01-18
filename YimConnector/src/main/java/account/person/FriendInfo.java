package account.person;

import future.Future;
import common.UserInfo;

/**
 * 查看好友个人信息接口
 *
 * Future future = new FriendInfo(username, friend).execute();
 * future.addListener(new FriendInfoFutureListener() {
 *     public void onSuccess(String username, String name, String sex, String age,
 *                           String phone, String address, String introduction) {
 *
 *         System.out.println("用户名：" + username);
 *         System.out.println("姓名：" + name);
 *         System.out.println("性别：" + sex);
 *         System.out.println("年龄：" + age);
 *         System.out.println("联系电话：" + phone);
 *         System.out.println("家庭住址：" + address);
 *         System.out.println("个人介绍：" + introduction);
 *     }
 *
 *     public void onFailure(String hint) {
 *         System.out.println("好友信息查询失败，错误提示：" + hint);
 *     }
 * });
 *
 * Created by yohann on 2017/1/18.
 */
public class FriendInfo {
    private String username;
    private String friend;

    public FriendInfo(String username, String friend) {
        this.username = username;
        this.friend = friend;
    }

    public Future execute() {
        FriendInfoReqPacket friendInfoReqPacket = new FriendInfoReqPacket(username, friend);
        UserInfo.channel.writeAndFlush(friendInfoReqPacket);
        return Future.getFuture();
    }
}
