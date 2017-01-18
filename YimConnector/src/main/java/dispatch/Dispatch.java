package dispatch;

import account.login.LoginRespPacket;
import account.person.AllFriendRespPacket;
import account.person.FriendInfoRespPacket;
import account.person.InfoLookRespPacket;
import account.person.InfoUpdateRespPacket;
import account.register.RegRespPacket;
import common.UserInfo;
import friends.*;
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

            case PacketType.FRIEND_REPLY_REQ:
                FriendReplyReqPacket friendReplyReqPacket = (FriendReplyReqPacket) packet;
                friendReplyReq(friendReplyReqPacket);
                break;

            case PacketType.FRIEND_REPLY_RESP:
                FriendReplyRespPacket friendReplyRespPacket = (FriendReplyRespPacket) packet;
                friendReplyResp(friendReplyRespPacket);
                break;

            case PacketType.FRIEND_REMOVE_RESP:
                FriendRemoveRespPacket friendRemoveRespPacket = (FriendRemoveRespPacket) packet;
                friendRemoveResp(friendRemoveRespPacket);
                break;

            case PacketType.INFO_UPDATE_RESP:
                InfoUpdateRespPacket infoUpdateRespPacket = (InfoUpdateRespPacket) packet;
                infoUpdateResp(infoUpdateRespPacket);
                break;

            case PacketType.INFO_LOOK_RESP:
                InfoLookRespPacket infoLookRespPacket = (InfoLookRespPacket) packet;
                infoLookResp(infoLookRespPacket);
                break;

            case PacketType.FRIEND_INFO_RESP:
                FriendInfoRespPacket friendInfoRespPacket = (FriendInfoRespPacket) packet;
                friendInfo(friendInfoRespPacket);
                break;

            case PacketType.ALL_FRIEND_RESP:
                AllFriendRespPacket allFriendRespPacket = (AllFriendRespPacket) packet;
                allFriend(allFriendRespPacket);
                break;
        }
    }

    /**
     * 处理登录响应
     *
     * @param loginRespPacket
     */
    private void login(LoginRespPacket loginRespPacket) {
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
    private void register(RegRespPacket regRespPacket) {
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
    private void personMsg(PersonMsgRespPacket personMsgRespPacket) {
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
    private void receivePersonMsg(PersonMsgReqPacket personMsgReqPacket) {
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
    private void friendAddResp(FriendAddRespPacket friendAddRespPacket) {
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
    private void friendAddReq(FriendAddReqPacket friendAddReqPacket) {
        String username = friendAddReqPacket.getUsername();
        String info = friendAddReqPacket.getInfo();
        Receiver receiver = Future.getFuture().getReceiver();
        receiver.receiveFriendAddReq(username, info);
        ReferenceCountUtil.release(friendAddReqPacket);
    }

    /**
     * 接收添加好友请求的回复消息
     *
     * @param friendReplyReqPacket
     */
    private void friendReplyReq(FriendReplyReqPacket friendReplyReqPacket) {
        String responser = friendReplyReqPacket.getUsername();
        boolean isAgree = friendReplyReqPacket.isAgree();
        Receiver receiver = Future.getFuture().getReceiver();
        receiver.receiveFriendReply(responser, isAgree);
        ReferenceCountUtil.release(friendReplyReqPacket);
    }

    /**
     * 接收回复添加好友的请求的响应消息
     *
     * @param friendReplyRespPacket
     */
    private void friendReplyResp(FriendReplyRespPacket friendReplyRespPacket) {
        FriendReplyFutureListener friendReplyFutureListener = Future.getFuture().getFriendReplyFutureListener();
        if (friendReplyRespPacket.isSuccess()) {
            friendReplyFutureListener.onSuccess();
        } else {
            friendReplyFutureListener.onFailure(friendReplyRespPacket.getHint());
        }
        ReferenceCountUtil.release(friendReplyRespPacket);
    }

    /**
     * 接收删除好友响应
     *
     * @param friendRemoveRespPacket
     */
    private void friendRemoveResp(FriendRemoveRespPacket friendRemoveRespPacket) {
        FriendRemoveFutureListener friendRemoveFutureListener = Future.getFuture().getFriendRemoveFutureListener();
        if (friendRemoveRespPacket.isSuccess()) {
            friendRemoveFutureListener.onSuccess();
        } else {
            friendRemoveFutureListener.onFailure(friendRemoveRespPacket.getHint());
        }
        ReferenceCountUtil.release(friendRemoveRespPacket);
    }

    /**
     * 接收修改信息响应
     *
     * @param infoUpdateRespPacket
     */
    private void infoUpdateResp(InfoUpdateRespPacket infoUpdateRespPacket) {
        InfoUpdateFutureListener infoUpdateFutureListener = Future.getFuture().getInfoUpdateFutureListener();
        if (infoUpdateRespPacket.isSuccess()) {
            infoUpdateFutureListener.onSuccess();
        } else {
            infoUpdateFutureListener.onFailure(infoUpdateRespPacket.getHint());
        }
        ReferenceCountUtil.release(infoUpdateRespPacket);
    }

    /**
     * 接收查看个人信息响应
     *
     * @param infoLookRespPacket
     */
    private void infoLookResp(InfoLookRespPacket infoLookRespPacket) {
        InfoLookFutureListener infoLookFutureListener = Future.getFuture().getInfoLookFutureListener();
        if (infoLookRespPacket.isSuccess()) {
            infoLookFutureListener.onSuccess(
                    infoLookRespPacket.getUsername(),
                    infoLookRespPacket.getName(),
                    infoLookRespPacket.getSex(),
                    infoLookRespPacket.getAge(),
                    infoLookRespPacket.getPhone(),
                    infoLookRespPacket.getAddress(),
                    infoLookRespPacket.getIntroduction());
        } else {
            infoLookFutureListener.onFailure(infoLookRespPacket.getHint());
        }
        ReferenceCountUtil.release(infoLookRespPacket);
    }

    /**
     * 接收好友信息
     *
     * @param friendInfoRespPacket
     */
    private void friendInfo(FriendInfoRespPacket friendInfoRespPacket) {
        FriendInfoFutureListener friendInfoFutureListener = Future.getFuture().getFriendInfoFutureListener();
        if (friendInfoRespPacket.isSuccess()) {
            friendInfoFutureListener.onSuccess(
                    friendInfoRespPacket.getUsername(),
                    friendInfoRespPacket.getName(),
                    friendInfoRespPacket.getSex(),
                    friendInfoRespPacket.getAge(),
                    friendInfoRespPacket.getPhone(),
                    friendInfoRespPacket.getAddress(),
                    friendInfoRespPacket.getIntroduction());
        } else {
            friendInfoFutureListener.onFailure(friendInfoRespPacket.getHint());
        }
        ReferenceCountUtil.release(friendInfoRespPacket);
    }

    /**
     * 接收已添加好友
     *
     * @param allFriendRespPacket
     */
    private void allFriend(AllFriendRespPacket allFriendRespPacket) {
        AllFriendFutureListener allFriendFutureListener = Future.getFuture().getAllFriendFutureListener();
        if (allFriendRespPacket.isSuccess()) {
            allFriendFutureListener.onExist(allFriendRespPacket.getFriendMap());
        } else {
            allFriendFutureListener.onNoExist();
        }
        ReferenceCountUtil.release(allFriendRespPacket);
    }
}
