package future;

/**
 * 监听好友个人信息
 *
 * Created by yohann on 2017/1/18.
 */
public interface FriendInfoFutureListener {
    void onSuccess(String username, String name, String sex, String age,
                   String phone, String address, String introduction);

    void onFailure(String hint);
}
