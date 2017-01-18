package account.person;

import future.Future;
import common.UserInfo;

/**
 * 查看个人信息接口
 *
 * Future future = new InfoLook(username).execute();
 * future.addListener(new InfoLookFutureListener() {
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
 *         System.out.println("个人信息查询失败，错误提示：" + hint);
 *     }
 * });
 *
 * Created by yohann on 2017/1/18.
 */
public class InfoLook {
    private String username;

    public InfoLook(String username) {
        this.username = username;
    }

    public Future execute() {
        InfoLookReqPacket infoLookReqPacket = new InfoLookReqPacket(username);
        UserInfo.channel.writeAndFlush(infoLookReqPacket);
        return Future.getFuture();
    }
}
