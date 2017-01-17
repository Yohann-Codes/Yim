package dispatch;

import account.login.LoginRespPacket;
import account.register.RegRespPacket;
import common.UserInfo;
import friends.FriendAddReqPacket;
import friends.FriendAddRespPacket;
import future.*;
import io.netty.util.ReferenceCountUtil;
import message.person.PersonMsgReqPacket;
import message.person.PersonMsgRespPacket;
import packet.Packet;
import packet.PacketType;
import transport.HeartbeatHandler;

/**
 * 分发并解析数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class Dispatch {
    private Packet packet;

    public Dispatch(Packet packet) {
        this.packet = packet;
    }

    public void deal() {
        switch (packet.getPacketType()) {

            case PacketType.LOGIN_RESP:
                LoginRespPacket loginRespPacket = (LoginRespPacket) packet;
                login(loginRespPacket);
                break;

            case PacketType.REGISTER_RESP:
                RegRespPacket regRespPacket = (RegRespPacket) packet;
                register(regRespPacket);
                break;

            case PacketType.PERSON_MSG_RESP:
                PersonMsgRespPacket personMsgRespPacket = (PersonMsgRespPacket) packet;
                personMsg(personMsgRespPacket);
                break;

            case PacketType.PERSON_MSG_REQ:
                PersonMsgReqPacket personMsgReqPacket = (PersonMsgReqPacket) packet;
                receivePersonMsg(personMsgReqPacket);
                break;

            case PacketType.FRIEND_ADD_RESP:
                FriendAddRespPacket friendAddRespPacket = (FriendAddRespPacket) packet;
                friendAddResp(friendAddRespPacket);
                break;

            case PacketType.FRIEND_ADD_REQ:
                FriendAddReqPacket friendAddReqPacket = (FriendAddReqPacket) packet;
                friendAddReq(friendAddReqPacket);
                break;
        }
    }

    /**
     * 处理登录响应
     *
     * @param loginRespPacket
     */
    public void login(LoginRespPacket loginRespPacket) {
        LoginFutureListener loginFutureListener = Future.getFuture().getLoginFutureListener();
        if (loginRespPacket.isSuccess()) {
            // 保存用户信息
            UserInfo.username = loginRespPacket.getUsername();
            UserInfo.token = loginRespPacket.getToken();
            // 启动心跳
            UserInfo.channel.pipeline().addAfter("IdleStateHandler",
                    "HeartbeatHandler", new HeartbeatHandler(UserInfo.username));

            loginFutureListener.onSuccess();
        } else {
            loginFutureListener.onFailure(loginRespPacket.getHint());
        }
        ReferenceCountUtil.release(loginRespPacket);
    }

    /**
     * 处理注册响应
     *
     * @param regRespPacket
     */
    public void register(RegRespPacket regRespPacket) {
        RegisterFutureListener registerFutureListener = Future.getFuture().getRegisterFutureListener();
        if (regRespPacket.isSuccess()) {
            registerFutureListener.onSuccess();
        } else {
            registerFutureListener.onFailure(regRespPacket.getHint());
        }
        ReferenceCountUtil.release(regRespPacket);
    }

    /**
     * 处理发送个人消息响应
     *
     * @param personMsgRespPacket
     */
    public void personMsg(PersonMsgRespPacket personMsgRespPacket) {
        PersonMsgFutureListener personMsgFutureListener = Future.getFuture().getPersonMsgFutureListener();
        if (personMsgRespPacket.isSuccess()) {
            personMsgFutureListener.onSuccess();
        } else {
            personMsgFutureListener.onFailure(personMsgRespPacket.getHint());
        }
        ReferenceCountUtil.release(personMsgRespPacket);
    }

    /**
     * 接收个人消息
     *
     * @param personMsgReqPacket
     */
    public void receivePersonMsg(PersonMsgReqPacket personMsgReqPacket) {
        String sender = personMsgReqPacket.getUsername();
        String message = personMsgReqPacket.getMessage();
        long time = personMsgReqPacket.getTime();
        Receiver receiver = Future.getFuture().getReceiver();
        receiver.receivePersonMessage(sender, message, time);
        ReferenceCountUtil.release(personMsgReqPacket);
    }

    /**
     * 接收添加好友响应信息
     *
     * @param friendAddRespPacket
     */
    public void friendAddResp(FriendAddRespPacket friendAddRespPacket) {
        FriendAddFutureListener friendAddFutureListener = Future.getFuture().getFriendAddFutureListener();
        if (friendAddRespPacket.isSuccess()) {
            friendAddFutureListener.onSuccess();
        } else {
            friendAddFutureListener.onFailure(friendAddRespPacket.getHint());
        }
        ReferenceCountUtil.release(friendAddRespPacket);
    }

    /**
     * 接收请求添加好友的消息
     *
     * @param friendAddReqPacket
     */
    public void friendAddReq(FriendAddReqPacket friendAddReqPacket) {
        String username = friendAddReqPacket.getUsername();
        String info = friendAddReqPacket.getInfo();
        Receiver receiver = Future.getFuture().getReceiver();
        receiver.receiveFriendAddReq(username, info);
        ReferenceCountUtil.release(friendAddReqPacket);
    }
}
