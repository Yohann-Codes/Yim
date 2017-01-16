package account.person;

import packet.PacketType;
import packet.Response;

/**
 * 查看好友详细信息响应数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class FriendInfoRespPacket extends Response {

    private String username;
    private String name;
    private String sex;
    private String age;
    private String phone;
    private String address;
    private String introduction;

    public FriendInfoRespPacket(boolean isSuccess, String hint,
                                String username, String name, String sex,
                                String age, String phone, String address,
                                String introduction) {
        super(isSuccess, hint);
        packetType = PacketType.FRIEND_INFO_RESP;
        this.username = username;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.introduction = introduction;
    }

    public String getUsername() {
        return username;
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
