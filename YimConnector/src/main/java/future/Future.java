package future;

/**
 * 作为接口返回值（单例模式）
 * 用于添加监听的网络响应数据的监听器
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class Future {
    private static Future future;

    private Receiver receiver;
    private LoginFutureListener loginFutureListener;
    private RegisterFutureListener registerFutureListener;
    private PersonMsgFutureListener personMsgFutureListener;
    private FriendAddFutureListener friendAddFutureListener;
    private FriendReplyFutureListener friendReplyFutureListener;

    private Future() {
    }

    public synchronized static Future getFuture() {
        if (future == null) {
            future = new Future();
            return future;
        } else {
            return future;
        }
    }

    public void addReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public void addListener(LoginFutureListener listener) {
        loginFutureListener = listener;
    }

    public void addListener(RegisterFutureListener listener) {
        registerFutureListener = listener;
    }

    public void addListener(PersonMsgFutureListener listener) {
        personMsgFutureListener = listener;
    }

    public void addListener(FriendAddFutureListener listener) {
        friendAddFutureListener = listener;
    }

    public void addListener(FriendReplyFutureListener listener) {
        friendReplyFutureListener = listener;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public LoginFutureListener getLoginFutureListener() {
        return loginFutureListener;
    }

    public RegisterFutureListener getRegisterFutureListener() {
        return registerFutureListener;
    }

    public PersonMsgFutureListener getPersonMsgFutureListener() {
        return personMsgFutureListener;
    }

    public FriendAddFutureListener getFriendAddFutureListener() {
        return friendAddFutureListener;
    }

    public FriendReplyFutureListener getFriendReplyFutureListener() {
        return friendReplyFutureListener;
    }
}
