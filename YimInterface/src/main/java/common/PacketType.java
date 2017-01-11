package common;

/**
 * 数据包类型
 *
 * Created by yohann on 2017/1/8.
 */
public class PacketType {
    // 心跳
    public static final int HEARTBEAT = 0x01;
    // 注册
    public static final int REGISTER = 0x02;
    // 登录
    public static final int LOGIN = 0x03;
    // 登出
    public static final int LOGOUT = 0x04;
    // 聊天
    public static final int CAHT = 0x05;
    // 申请添加好友
    public static final int ADD_FRIEND = 0x06;
    // 同意添加好友
    public static final int AGREE_FRIEND = 0x07;
    // 删除好友
    public static final int REMOVE_FRIEND = 0x08;
    // 好友上线
    public static final int ONLINE = 0x09;
    // 好友离线
    public static final int OFFLINE = 0x0a;
    // 已添加的好友
    public static final int ALL_FRIEND = 0x0b;
    // 用户详细信息
    public static final int USER_INFO = 0x0c;
    // 登录响应数据包
    public static final int RESP_LOGIN = 0x0d;
    // 注册响应数据包
    public static final int RESP_REG = 0x0e;
}
