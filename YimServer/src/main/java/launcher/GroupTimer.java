package launcher;

import message.group.GroupManager;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * 启动一个定时任务，每个2min检查一次Group最后一次活跃时间
 * 如果超过5min，就从内存中remove掉。
 *
 * Created by yohann on 2017/1/20.
 */
public class GroupTimer {
    private static final Logger LOGGER = Logger.getLogger(GroupTimer.class);

    public GroupTimer() {
    }

    public void start() {
        Timer timer = new Timer();
        timer.schedule(new GroupTimerTask(), 0, 120000);
    }

    class GroupTimerTask extends TimerTask {

        public void run() {
            LOGGER.info("执行定时任务");
            LOGGER.info("内存 Groups = " + GroupManager.getGroups().size() + " GroupTimes = " + GroupManager.getGroupTimes().size());
            Set<Map.Entry<String, Long>> entrySet = GroupManager.getGroupTimesIte();
            if (entrySet.size() > 0) {
                Iterator<Map.Entry<String, Long>> ite = entrySet.iterator();
                while (ite.hasNext()) {
                    Map.Entry<String, Long> entry = ite.next();
                    String groupName = entry.getKey();
                    Long time = entry.getValue();
                    if ((System.currentTimeMillis() - time) > 300000) {
                        // 调出内存
                        GroupManager.groupTimesRemove(groupName);
                        GroupManager.groupRemove(groupName);
                        LOGGER.info("讨论组<" + groupName + "> 已被调出内存" );
                    }
                }
            }
            LOGGER.info("内存 Groups = " + GroupManager.getGroups().size() + " GroupTimes = " + GroupManager.getGroupTimes().size());
        }
    }
}
