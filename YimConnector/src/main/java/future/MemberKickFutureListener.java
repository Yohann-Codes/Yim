package future;

/**
 * 监听踢出成员接口
 *
 * Created by yohann on 2017/1/19.
 */
public interface MemberKickFutureListener {
    void onSuccess(String groupName, String member);

    void onFailure(String hint);
}
