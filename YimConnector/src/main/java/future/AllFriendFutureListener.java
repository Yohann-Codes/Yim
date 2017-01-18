package future;

import java.util.Map;

/**
 * 监听已添加好友
 *
 * Created by yohann on 2017/1/18.
 */
public interface AllFriendFutureListener {
    void onExist(Map<String, Boolean> friends);

    void onNoExist();
}
