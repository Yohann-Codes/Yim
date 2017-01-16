package dispatch;

import account.login.LoginRespPacket;
import account.register.RegRespPacket;
import common.UserInfo;
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
                ReferenceCountUtil.release(loginRespPacket);
                break;

            case PacketType.REGISTER_RESP:
                RegRespPacket regRespPacket = (RegRespPacket) packet;
                register(regRespPacket);
                ReferenceCountUtil.release(regRespPacket);
                break;

            case PacketType.PERSON_MSG_RESP:
                PersonMsgRespPacket personMsgRespPacket = (PersonMsgRespPacket) packet;
                personMsgReq(personMsgRespPacket);
                ReferenceCountUtil.release(personMsgRespPacket);
                break;

            case PacketType.PERSON_MSG_REQ:
                PersonMsgReqPacket personMsgReqPacket = (PersonMsgReqPacket) packet;
                receivePersonMsg(personMsgReqPacket);
                ReferenceCountUtil.release(personMsgReqPacket);
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
    }

    /**
     * 处理发送个人消息响应
     *
     * @param personMsgRespPacket
     */
    public void personMsgReq(PersonMsgRespPacket personMsgRespPacket) {
        PersonMsgFutureListener personMsgFutureListener = Future.getFuture().getPersonMsgFutureListener();
        if (personMsgRespPacket.isSuccess()) {
            personMsgFutureListener.onSuccess();
        } else {
            personMsgFutureListener.onFailure(personMsgRespPacket.getHint());
        }
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
    }
}
