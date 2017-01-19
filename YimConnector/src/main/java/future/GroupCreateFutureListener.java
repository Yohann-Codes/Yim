package future;

/**
 * 监听创建讨论组响应
 *
 * Created by yohann on 2017/1/19.
 */
public interface GroupCreateFutureListener {
    void onSuccess(String groupName);

    void onFailure(String hint);
}
