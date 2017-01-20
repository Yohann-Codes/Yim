package future;

/**
 * 监听讨论组消息响应
 *
 * Created by yohann on 2017/1/20.
 */
public interface GroupMsgFutureListener {
    void onSuccess();

    void onFailure(String hint);
}
