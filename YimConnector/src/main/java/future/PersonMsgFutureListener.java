package future;

/**
 * 监听个人消息响应
 * <p>
 * Created by yohann on 2017/1/16.
 */
public interface PersonMsgFutureListener {
    void onSuccess();

    void onFailure(String hint);
}
