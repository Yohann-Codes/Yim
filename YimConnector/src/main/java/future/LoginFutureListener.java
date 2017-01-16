package future;

import account.login.LoginRespPacket;

/**
 * 监听登录响应
 * <p>
 * Created by yohann on 2017/1/14.
 */
public interface LoginFutureListener {
    void onFinishLogin(LoginRespPacket packet);
}
