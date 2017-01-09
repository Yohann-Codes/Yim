package packet;

import common.Packet;
import common.PacketType;

/**
 * 用户详细信息
 *
 * Created by yohann on 2017/1/8.
 */
public class UserInfoPacket extends Packet {

    // 用户名
    private String username;
    // 密码
    private String password;
    // 姓名
    private String name;
    // 性别
    private String sex;
    // 年龄
    private String age;
    // 联系电话
    private String phone;
    // 家庭地址
    private String address;
    // 自我介绍
    private String introduction;

    public UserInfoPacket() {
        packetType = PacketType.USER_INFO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserInfoPacket{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
