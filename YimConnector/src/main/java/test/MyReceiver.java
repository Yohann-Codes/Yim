package test;

import future.Receiver;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 接收消息的类
 * <p>
 * Created by yohann on 2017/1/16.
 */
public class MyReceiver implements Receiver {
    /**
     * 接收个人消息
     *
     * @param sender
     * @param message
     * @param time
     */
    public void receivePersonMessage(String sender, String message, long time) {
        String t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
        System.out.println("" + sender + ": " + message + "   [" + t + "]");
    }

    public void receiveFriendAddReq(String requester, String info) {
        System.out.println(requester + " 请求添加你为好友  [备注：" + info + "]");
    }
}
