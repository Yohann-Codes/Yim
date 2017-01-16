package account.person;

import packet.PacketType;
import packet.Request;

/**
 * 修改个人详细信息请求数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class InfoUpdateReqPacket extends Request {

    private String password;
    private String name;
    private String sex;
    private String age;
    private String phone;
    private String address;
    private String introduction;

    public InfoUpdateReqPacket(String username, String password,
                               String name, String sex, String age,
                               String phone, String address, String introduction) {
        super(username);
        packetType = PacketType.INFO_UPDATE_REQ;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.introduction = introduction;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getIntroduction() {
        return introduction;
    }
}
