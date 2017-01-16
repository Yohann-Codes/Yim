package message.person;

import common.UserInfo;
import future.Future;

/**
 * 发送个人消息接口
 *
 * Future future = new PersonMsg(receiver, message).execute();
 *
 * future.addListener(new PersonMsgFutureListener() {
 *     public void onSuccess() {
 *         System.out.println("发送成功");
 *     }
 *
 *     public void onFailure(String hint) {
 *         System.out.println("发送失败，错误提示：" + hint);
 *     }
 * });
 *
 * Created by yohann on 2017/1/16.
 */
public class PersonMsg {
    private String receiver;
    private String message;

    public PersonMsg(String receiver, String message) {
        this.receiver = receiver;
        this.message = message;
    }

    public Future execute() {
        PersonMsgReqPacket personMsgReqPacket =
                new PersonMsgReqPacket(UserInfo.username, receiver, message, System.currentTimeMillis());
        UserInfo.channel.writeAndFlush(personMsgReqPacket);
        return Future.getFuture();
    }
}
