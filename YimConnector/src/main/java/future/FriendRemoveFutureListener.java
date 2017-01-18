package future;

/**
 * 监听删除好友响应
 *
 * Created by yohann on 2017/1/18.
 */
public interface FriendRemoveFutureListener {
    void onSuccess();

    void onFailure(String hint);
}
