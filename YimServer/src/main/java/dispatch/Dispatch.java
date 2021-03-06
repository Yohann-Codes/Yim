package dispatch;

import account.login.LoginLogic;
import account.login.LoginReqPacket;
import account.logout.LogoutLogic;
import account.logout.LogoutReqPacket;
import account.person.*;
import account.register.RegReqPacket;
import account.register.RegisterLogic;
import account.relogin.ReLoginLogic;
import account.relogin.ReLoginReqPacket;
import friends.*;
import groups.*;
import io.netty.channel.Channel;
import message.group.GroupMsgLogic;
import message.group.GroupMsgReqPacket;
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
                new FriendAddLogic(friendAddReqPacket, channel).deal();
                break;

            case PacketType.FRIEND_REPLY_REQ:
                FriendReplyReqPacket friendReplyReqPacket = (FriendReplyReqPacket) packet;
                new FriendReplyLogic(friendReplyReqPacket, channel).deal();
                break;

            case PacketType.FRIEND_REMOVE_REQ:
                FriendRemoveReqPacket friendRemoveReqPacket = (FriendRemoveReqPacket) packet;
                new FriendRemoveLogic(friendRemoveReqPacket, channel).deal();
                break;

            case PacketType.INFO_UPDATE_REQ:
                InfoUpdateReqPacket infoUpdateReqPacket = (InfoUpdateReqPacket) packet;
                new InfoUpdateLogic(infoUpdateReqPacket, channel).deal();
                break;

            case PacketType.INFO_LOOK_REQ:
                InfoLookReqPacket infoLookReqPacket = (InfoLookReqPacket) packet;
                new InfoLookLogic(infoLookReqPacket, channel).deal();
                break;

            case PacketType.FRIEND_INFO_REQ:
                FriendInfoReqPacket friendInfoReqPacket = (FriendInfoReqPacket) packet;
                new FriendInfoLogic(friendInfoReqPacket, channel).deal();
                break;

            case PacketType.ALL_FRIEND_REQ:
                AllFriendReqPacket allFriendReqPacket = (AllFriendReqPacket) packet;
                new AllFriendLogic(allFriendReqPacket, channel).deal();
                break;

            case PacketType.GROUP_CREATE_REQ:
                GroupCreateReqPacket groupCreateReqPacket = (GroupCreateReqPacket) packet;
                new GroupCreateLogic(groupCreateReqPacket, channel).deal();
                break;

            case PacketType.GROUP_DISBAND_REQ:
                GroupDisbandReqPacket groupDisbandReqPacket = (GroupDisbandReqPacket) packet;
                new GroupDisbandLogic(groupDisbandReqPacket, channel).deal();
                break;

            case PacketType.MEMBER_INVITE_REQ:
                MemberInviteReqPacket memberInviteReqPacket = (MemberInviteReqPacket) packet;
                new MemberInviteLogic(memberInviteReqPacket, channel).deal();
                break;

            case PacketType.MEMBER_KICK_REQ:
                MemberKickReqPacket memberKickReqPacket = (MemberKickReqPacket) packet;
                new MemberKickLogic(memberKickReqPacket, channel).deal();
                break;

            case PacketType.ALL_GROUPS_REQ:
                AllGroupsReqPacket allGroupsReqPacket = (AllGroupsReqPacket) packet;
                new AllGroupsLogic(allGroupsReqPacket, channel).deal();
                break;

            case PacketType.GROUP_MSG_REQ:
                GroupMsgReqPacket groupMsgReqPacket = (GroupMsgReqPacket) packet;
                new GroupMsgLogic(groupMsgReqPacket, channel).deal();
                break;

            case PacketType.RE_LOGIN_REQ:
                ReLoginReqPacket reLoginReqPacket = (ReLoginReqPacket) packet;
                new ReLoginLogic(reLoginReqPacket, channel).deal();
                break;
        }
    }
}
