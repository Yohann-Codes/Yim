package launcher;

import common.Config;
import org.apache.log4j.Logger;

/**
 * 启动类
 * <p>
 * Created by yohann on 2017/1/8.
 */
public class Launcher {
    private static final Logger LOGGER = Logger.getLogger(Launcher.class);

    public static void main(String[] args) {
        LOGGER.info("开始启动服务器程序");
        // 配置线程池大小，监听端口
        new Service().start(Config.PORT);
    }
}
