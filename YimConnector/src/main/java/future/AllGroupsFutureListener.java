package future;

import java.util.List;
import java.util.Map;

/**
 * 监听所在全部讨论组响应信息
 *
 * Created by yohann on 2017/1/19.
 */
public interface AllGroupsFutureListener {
    void onReceiveAllGroups(Map<String, List<String>> groups);
}
