package future;

/**
 * 监听修改信息响应
 * <p>
 * Created by yohann on 2017/1/18.
 */
public interface InfoUpdateFutureListener {
    void onSuccess();

    void onFailure(String hint);
}
