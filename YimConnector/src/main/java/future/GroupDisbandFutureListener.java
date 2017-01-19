package future;

/**
 * 监听解散讨论组响应
 *
 * Created by yohann on 2017/1/19.
 */
public interface GroupDisbandFutureListener {
    void onSuccess(String groupName);

    void onFailure(String hint);
}
