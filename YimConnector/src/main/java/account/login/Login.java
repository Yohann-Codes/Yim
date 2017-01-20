package account.login;

import account.OnConnectionListener;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import common.Constants;
import common.UserInfo;
import future.Future;
import transport.Service;

/**
 * 登录接口
 *
 * Future future = new Login(username, password).execute();
 * future.addListener(new LoginFutureListener() {
 *     public void onSuccess() {
 *         System.out.println("登录成功");
 *     }
 *
 *     public void onFailure(String hint) {
 *         System.out.println("登录失败，错误提示：" + hint);
 *     }
 * });
 *
 * Created by yohann on 2017/1/14.
 */
public class Login implements OnConnectionListener {
    private String username;
    private String password;
    private Long token;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Login(Long token) {
        this.token = token;
    }

    /**
     * 客户端调用此方法执行登录
     */
    public Future execute() {
        new Thread() {
            @Override
            public void run() {
                new Service(Login.this).connect(Constants.HOST, Constants.PORT);
            }
        }.start();

        return Future.getFuture();
    }

    public void onConnetionComplete() {
        System.out.println("连接成功 --> 开始登录");
        if (token == null) {
            // 登录
            login();
        } else {
            // 重连
            reconnect();
        }
    }

    /**
     * 登录
     */
    private void login() {
        LoginReqPacket loginReqPacket = new LoginReqPacket(username, password);
        UserInfo.channel.writeAndFlush(loginReqPacket);
    }

    /**
     * 重连
     */
    private void reconnect() {
        ReLoginReqPacket reLoginReqPacket = new ReLoginReqPacket(UserInfo.username, token);
        UserInfo.channel.writeAndFlush(reLoginReqPacket);
    }
}