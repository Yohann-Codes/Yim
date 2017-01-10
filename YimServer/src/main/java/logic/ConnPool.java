package logic;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 连接池
 * <p>
 * Created by yohann on 2017/1/10.
 */
public class ConnPool {

    // 用于存放在线用户的username和ChannelHandlerContext
    private static Map<String, ChannelHandlerContext> connsMap =
            new HashMap<String, ChannelHandlerContext>();

    /**
     * 添加连接
     *
     * @param username
     * @param ctx
     * @return
     */
    public synchronized static boolean add(String username, ChannelHandlerContext ctx) {
        ChannelHandlerContext result = connsMap.put(username, ctx);
        if (result == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除连接
     *
     * @param username
     * @return
     */
    public synchronized static boolean remove(String username) {
        ChannelHandlerContext result = connsMap.remove(username);
        if (result != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查找连接
     *
     * @param username
     * @return
     */
    public synchronized static ChannelHandlerContext query(String username) {
        return connsMap.get(username);
    }

    /**
     * 查找连接
     *
     * @param ctx
     * @return
     */
    public synchronized static String query(ChannelHandlerContext ctx) {
        Set<Map.Entry<String, ChannelHandlerContext>> entries = connsMap.entrySet();
        Iterator<Map.Entry<String, ChannelHandlerContext>> ite = entries.iterator();
        while (ite.hasNext()) {
            Map.Entry<String, ChannelHandlerContext> entry = ite.next();
            if (ctx.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
