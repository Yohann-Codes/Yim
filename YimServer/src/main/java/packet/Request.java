package packet;

/**
 * 所有请求数据包都继承此类
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class Request extends Packet {

    protected String username;

    protected Request(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
