package future;

/**
 * 作为接口返回值
 * 用于添加监听的网络响应数据的监听器
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class Future {

    private LoginFutureListener loginFutureListener;
    private RegisterFutureListener registerFutureListener;

    public void addListener(LoginFutureListener listener) {
        loginFutureListener = listener;
    }

    public void addListener(RegisterFutureListener listener) {
        registerFutureListener = listener;
    }

    public LoginFutureListener getLoginFutureListener() {
        return loginFutureListener;
    }

    public RegisterFutureListener getRegisterFutureListener() {
        return registerFutureListener;
    }
}
