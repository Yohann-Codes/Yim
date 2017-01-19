package future;

/**
 * 监听邀请成员响应
 *
 * Created by yohann on 2017/1/19.
 */
public interface MemberInviteFutureListener {
    void onSuccess();

    void onFailure(String hint);
}
