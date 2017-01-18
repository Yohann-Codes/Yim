package account.person;

import future.Future;
import common.UserInfo;

/**
 * 修改个人信息接口
 *
 * 以修改密码为例：
 *
 * InfoUpdate infoUpdate = new InfoUpdate(username);
 * infoUpdate.setPassword(newPassword);
 * Future future = infoUpdate.execute();
 * future.addListener(new InfoUpdateFutureListener() {
 *     public void onSuccess() {
 *         System.out.println("密码修改成功");
 *     }
 *
 *     public void onFailure(String hint) {
 *         System.out.println("密码修改失败，错误提示：" + hint);
 *     }
 * });
 *
 * Created by yohann on 2017/1/18.
 */
public class InfoUpdate {

    private String username;
    private String password;
    private String name;
    private String sex;
    private String age;
    private String phone;
    private String address;
    private String introduction;

    public InfoUpdate(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Future execute() {
        InfoUpdateReqPacket infoUpdateReqPacket =
                new InfoUpdateReqPacket(username, password, name,
                        sex, age, phone, address, introduction);
        UserInfo.channel.writeAndFlush(infoUpdateReqPacket);
        return Future.getFuture();
    }
}
