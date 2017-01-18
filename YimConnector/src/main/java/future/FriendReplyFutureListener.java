package future;

/**
 * 监听好友请求回复响应
 *
 * Created by yohann on 2017/1/18.
 */
public interface FriendReplyFutureListener {
    void onSuccess();

    void onFailure(String hint);
}
