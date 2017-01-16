package message;

import packet.Request;

/**
 * 所有请求发送消息数据包都继承此类
 * <p>
 * Created by yohann on 2017/1/13.
 */
public class Message extends Request {

    protected String message;
    protected long time;

    protected Message(String username, String message, long time) {
        super(username);
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }
}
