package log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志工具
 * <p>
 * Created by yohann on 2017/1/10.
 */
public class MyLog {
    /**
     * 系统级别
     *
     * @param msg
     */
    public static void sysLogger(String msg) {
        System.out.println("System ->  " + msg + "  " + Thread.currentThread().getName() + "  " +
                new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss")
                        .format(new Date(System.currentTimeMillis())));
    }

    /**
     * 用户级别
     *
     * @param msg
     */
    public static void userLogger(String msg) {
        System.out.println("Users ->  " + msg + "  " +  Thread.currentThread().getName() + "  " +
                new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss")
                        .format(new Date(System.currentTimeMillis())));
    }
}
