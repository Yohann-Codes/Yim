package future;

/**
 * 用户需要实现此接口来接收服务器发来的消息及通知
 *
 * // 首先在别的类中注册消息接收器
 * Future.getFuture().addReceiver(new MyReceiver());
 *
 * public class MyReceiver implements Receiver {
 *     public void receivePersonMessage(String sender, String message, long time) {
 *         String t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
 *         System.out.println("" + sender + ": " + message + "   [" + t + "]");
 *     }
 * }
 *
 * Created by yohann on 2017/1/16.
 */
public interface Receiver {
    /**
     * 重写此方法来接收个人消息
     *
     * @param sender
     * @param message
     * @param time
     */
    void receivePersonMessage(String sender, String message, long time);
}
