package packet;

/**
 * 所有响应数据包都继承此类
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class Response extends Packet {

    protected boolean isSuccess;
    // 如果请求成功，hint->null；如果失败，hint->失败原因
    protected String hint;

    protected Response(boolean isSuccess, String hint) {
        this.isSuccess = isSuccess;
        this.hint = hint;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getHint() {
        return hint;
    }
}
