package future;

/**
 * 监听注册响应
 * <p>
 * Created by yohann on 2017/1/16.
 */
public interface RegisterFutureListener {
    void onSuccess();

    void onFailure(String hint);
}
