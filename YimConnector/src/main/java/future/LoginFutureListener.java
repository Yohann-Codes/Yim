package future;

/**
 * 监听登录响应
 * <p>
 * Created by yohann on 2017/1/14.
 */
public interface LoginFutureListener {
    void onSuccess();

    void onFailure(String hint);
}
