package packet;

/**
 * 数据包类型
 * <p>
 * Created by yohann on 2017/1/8.
 */
public class PacketType {
    // 心跳
    public static final int HEARTBEAT = 0x01;
    // 登录请求
    public static final int LOGIN_REQ = 0x02;
    // 登录响应
    public static final int LOGIN_RESP = 0x03;
    // 注册请求
    public static final int REGISTER_REQ = 0x04;
    // 注册响应
    public static final int REGISTER_RESP = 0x05;
    // 个人消息请求
    public static final int PERSON_MSG_REQ = 0x06;
    // 个人消息响应
    public static final int PERSON_MSG_RESP = 0x07;
    // 讨论组消息请求
    public static final int GROUP_MSG_REQ = 0x08;
    // 讨论组消息响应
    public static final int GROUP_MSG_RESP = 0x09;
    // 创建讨论组请求
    public static final int GROUP_CREATE_REQ = 0x0a;
    // 创建讨论组响应
    public static final int GROUP_CREATE_RESP = 0x0b;
    // 解散讨论组请求
    public static final int GROUP_DISBAND_REQ = 0x0c;
    // 解散讨论组响应
    public static final int GROUP_DISBAND_RESP = 0x0d;
    // 邀请成员请求
    public static final int MEMBER_INVITE_REQ = 0x0e;
    // 邀请成员响应
    public static final int MEMBER_INVITE_RESP = 0x0f;
    // 邀请答复请求
    public static final int MEMBER_REPLY_REQ = 0x10;
    // 邀请答复响应
    public static final int MEMBER_REPLY_RESP = 0x11;
    // 踢出成员请求
    public static final int MEMBER_KICK_REQ = 0x12;
    // 踢出成员响应
    public static final int MEMBER_KICK_RESP = 0x13;
    // 退出讨论组请求
    public static final int MEMBER_LEAVE_REQ = 0x14;
    // 退出讨论组响应
    public static final int MEMBER_LEAVE_RESP = 0x15;
    // 添加好友请求
    public static final int FRIEND_ADD_REQ = 0x16;
    // 添加好友响应
    public static final int FRIEND_ADD_RESP = 0x17;
    // 答复添加好友请求
    public static final int FRIEND_REPLY_REQ = 0x18;
    // 答复添加好友响应
    public static final int FRIEND_REPLY_RESP = 0x19;
    // 删除好友请求
    public static final int FRIEND_REMOVE_REQ = 0x1a;
    // 删除好友响应
    public static final int FRIEND_REMOVE_RESP = 0x1b;
    // 修改个人详细信息请求
    public static final int INFO_UPDATE_REQ = 0x1c;
    // 修改个人详细信息响应
    public static final int INFO_UPDATE_RESP = 0x1d;
    // 查看个人详细信息请求
    public static final int INFO_LOOK_REQ = 0x1e;
    // 查看个人详细信息响应
    public static final int INFO_LOOK_RESP = 0x1f;
    // 查看好友详细信息请求
    public static final int FRIEND_INFO_REQ = 0x20;
    // 查看好友详细信息响应
    public static final int FRIEND_INFO_RESP = 0x21;
    // 查看全部好友请求
    public static final int ALL_FRIEND_REQ = 0x22;
    // 查看全部好友响应
    public static final int ALL_FRIEND_RESP = 0x23;
    // 查看所在的全部讨论组请求
    public static final int ALL_GROUPS_REQ = 0x24;
    // 查看所在的全部讨论组响应
    public static final int ALL_GROUPS_RESP = 0x25;
    // 服务器给客户端发送的通知
    public static final int NOTICE = 0x26;
    // 登出请求
    public static final int LOGOUT_REQ = 0x27;
    // 重连请求
    public static final int RE_LOGIN_REQ = 0x28;
    // 重连响应
    public static final int RE_LOGIN_RESP = 0x29;
}
