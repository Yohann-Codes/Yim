package launcher;

import common.SerConstants;
import transport.Service;

/**
 * 启动类
 * <p>
 * Created by yohann on 2017/1/8.
 */
public class Launcher {
    public static void main(String[] args) {
        // 配置线程池大小和端口
        System.out.println("-- 开始启动服务器程序 --");
        new Service(SerConstants.NTHREADS).start(SerConstants.PORT);
    }
}
