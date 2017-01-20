package common;

import io.netty.channel.Channel;

import java.util.List;

/**
 * 保存用户登录的信息
 * <p>
 * Created by yohann on 2017/1/10.
 */
public class UserInfo {
    /**
     * 用户名
     */
    public static String username;

    /**
     * 消息传输
     */
    public static Channel channel;

    /**
     * 断线重连的验证信息
     */
    public static Long token;

    /**
     * 讨论组
     */
    public static List<String> groups;

    /**
     * 登出标志
     */
    public static boolean isLogout = false;
}
