package future;

/**
 * 用户需要实现此接口来接收服务器发来的消息及通知
 *
 * // 首先在别的类中注册消息接收器
 * Future.getFuture().addReceiver(new MyReceiver());
 *
 * // 实现Receiver接口
 * public class MyReceiver implements Receiver {
 *
 *     public void receivePersonMessage(String sender, String message, long time) {
 *         String t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
 *         System.out.println(sender + ": " + message + "   [" + t + "]");
 *     }
 *
 *     public void receiveGroupMessage(String groupName, String sender, String message, long time) {
 *         String t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
 *         System.out.println("<" + groupName + "> " + sender + ": " + message + "   [" + t + "]");
 *     }
 *
 *     public void receiveFriendAddReq(String requester, String info) {
 *         System.out.println(requester + " 请求添加你为好友   备注：" + info);
 *     }
 *
 *     public void receiveFriendReply(String responser, boolean isArgee) {
 *         if (isArgee) {
 *             System.out.println(responser + " 同意添加好友");
 *         } else {
 *             System.out.println(responser + " 拒绝添加好友");
 *         }
 *     }
 * }
 *
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

    /**
     * 重写此方法来接收讨论组消息
     *
     * @param groupName
     * @param sender
     * @param message
     * @param time
     */
    void receiveGroupMessage(String groupName, String sender, String message, long time);

    /**
     * 重写此方法来接收请求添加好友的通知
     *
     * @param requester
     * @param info
     */
    void receiveFriendAddReq(String requester, String info);

    /**
     * 重写此方法来接收添加好友的请求的处理结果
     *
     * @param responser
     * @param isArgee
     */
    void receiveFriendReply(String responser, boolean isArgee);

    /**
     * 重连此方法来接收断线重连响应结果
     *
     * @param isSuccess
     * @param hint
     */
    void reconnectResp(boolean isSuccess, String hint);
}
