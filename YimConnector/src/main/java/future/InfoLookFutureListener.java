package future;

/**
 * 监听查看个人信息响应
 *
 * Created by yohann on 2017/1/18.
 */
public interface InfoLookFutureListener {
    void onSuccess(String username, String name, String sex, String age,
                   String phone, String address, String introduction);

    void onFailure(String hint);
}
