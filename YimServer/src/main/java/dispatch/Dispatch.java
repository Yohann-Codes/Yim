package dispatch;

import account.login.LoginLogic;
import account.login.LoginReqPacket;
import account.logout.LogoutLogic;
import account.logout.LogoutReqPacket;
import account.register.RegReqPacket;
import account.register.RegisterLogic;
import friends.FriendAddReqLogic;
import friends.FriendAddReqPacket;
import io.netty.channel.Channel;
import message.person.PersonMsgLogic;
import message.person.PersonMsgReqPacket;
import packet.Packet;
import packet.PacketType;

/**
 * 分发数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class Dispatch implements Runnable {

    private Packet packet;
    private Channel channel;

    public Dispatch(Packet packet, Channel channel) {
        this.packet = packet;
        this.channel = channel;
    }

    public void run() {
        switch (packet.getPacketType()) {

            case PacketType.LOGIN_REQ:
                LoginReqPacket loginReqPacket = (LoginReqPacket) packet;
                new LoginLogic(loginReqPacket, channel).deal();
                break;

            case PacketType.LOGOUT_REQ:
                LogoutReqPacket logoutReqPacket = (LogoutReqPacket) packet;
                new LogoutLogic(logoutReqPacket, channel).deal();
                break;

            case PacketType.REGISTER_REQ:
                RegReqPacket regReqPacket = (RegReqPacket) packet;
                new RegisterLogic(regReqPacket, channel).deal();
                break;

            case PacketType.PERSON_MSG_REQ:
                PersonMsgReqPacket personMsgReqPacket = (PersonMsgReqPacket) packet;
                new PersonMsgLogic(personMsgReqPacket, channel).deal();
                break;

            case PacketType.FRIEND_ADD_REQ:
                FriendAddReqPacket friendAddReqPacket = (FriendAddReqPacket) packet;
                new FriendAddReqLogic(friendAddReqPacket, channel).deal();
                break;
        }
    }
}
