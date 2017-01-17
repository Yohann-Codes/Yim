package future;

/**
 * 监听添加好友响应
 *
 * Created by yohann on 2017/1/17.
 */
public interface FriendAddFutureListener {
    void onSuccess();

    void onFailure(String hint);
}
