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

    public void receivePersonMessage(String sender, String message, long time) {
        String t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
        System.out.println(sender + ": " + message + "   [" + t + "]");
    }

    public void receiveGroupMessage(String groupName, String sender, String message, long time) {
        String t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
        System.out.println("<" + groupName + "> " + sender + ": " + message + "   [" + t + "]");
    }

    public void receiveFriendAddReq(String requester, String info) {
        System.out.println(requester + " 请求添加你为好友   备注：" + info);
    }

    public void receiveFriendReply(String responser, boolean isArgee) {
        if (isArgee) {
            System.out.println(responser + " 同意添加好友");
        } else {
            System.out.println(responser + " 拒绝添加好友");
        }
    }

    public void reconnectResp(boolean isSuccess, String hint) {
        if (isSuccess) {
            System.out.println("重新连接服务器成功");
        } else {
            System.out.println("重新连接服务器失败，错误提示：" + hint);
        }
    }
}
