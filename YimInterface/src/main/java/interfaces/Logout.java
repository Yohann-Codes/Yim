package interfaces;

import common.CacheVars;

/**
 * 登出
 * <p>
 * Created by yohann on 2017/1/10.
 */
public class Logout {
    public void execute() {
        CacheVars.ctx.channel().close();
    }
}
