package future;

import account.register.RegRespPacket;

/**
 * 监听注册响应
 * <p>
 * Created by yohann on 2017/1/16.
 */
public interface RegisterFutureListener {
    void onFinishRegister(RegRespPacket regRespPacket);
}
