package future;

/**
 * 作为接口返回值（单例模式）
 * 用于添加监听的网络响应数据的监听器
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class Future {
    private static Future future;

    private Receiver receiver;
    private LoginFutureListener loginFutureListener;
    private RegisterFutureListener registerFutureListener;
    private PersonMsgFutureListener personMsgFutureListener;
    private FriendAddFutureListener friendAddFutureListener;
    private FriendReplyFutureListener friendReplyFutureListener;
    private FriendRemoveFutureListener friendRemoveFutureListener;
    private InfoUpdateFutureListener infoUpdateFutureListener;
    private InfoLookFutureListener infoLookFutureListener;
    private FriendInfoFutureListener friendInfoFutureListener;
    private AllFriendFutureListener allFriendFutureListener;
    private GroupCreateFutureListener groupCreateFutureListener;
    private GroupDisbandFutureListener groupDisbandFutureListener;
    private MemberInviteFutureListener memberInviteFutureListener;
    private MemberKickFutureListener memberKickFutureListener;
    private AllGroupsFutureListener allGroupsFutureListener;
    private GroupMsgFutureListener groupMsgFutureListener;

    private Future() {
    }

    public synchronized static Future getFuture() {
        if (future == null) {
            future = new Future();
            return future;
        } else {
            return future;
        }
    }

    public void addReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public void addListener(LoginFutureListener listener) {
        loginFutureListener = listener;
    }

    public void addListener(RegisterFutureListener listener) {
        registerFutureListener = listener;
    }

    public void addListener(PersonMsgFutureListener listener) {
        personMsgFutureListener = listener;
    }

    public void addListener(FriendAddFutureListener listener) {
        friendAddFutureListener = listener;
    }

    public void addListener(FriendReplyFutureListener listener) {
        friendReplyFutureListener = listener;
    }

    public void addListener(FriendRemoveFutureListener listener) {
        friendRemoveFutureListener = listener;
    }

    public void addListener(InfoUpdateFutureListener listener) {
        infoUpdateFutureListener = listener;
    }

    public void addListener(InfoLookFutureListener listener) {
        infoLookFutureListener = listener;
    }

    public void addListener(FriendInfoFutureListener listener) {
        friendInfoFutureListener = listener;
    }

    public void addListener(AllFriendFutureListener listener) {
        allFriendFutureListener = listener;
    }

    public void addListener(GroupCreateFutureListener listener) {
        groupCreateFutureListener = listener;
    }

    public void addListener(GroupDisbandFutureListener listener) {
        groupDisbandFutureListener = listener;
    }

    public void addListener(MemberInviteFutureListener listener) {
        memberInviteFutureListener = listener;
    }

    public void addListener(MemberKickFutureListener listener) {
        memberKickFutureListener = listener;
    }

    public void addListener(AllGroupsFutureListener listener) {
        allGroupsFutureListener = listener;
    }

    public void addListener(GroupMsgFutureListener listener) {
        groupMsgFutureListener = listener;
    }


    public Receiver getReceiver() {
        return receiver;
    }

    public LoginFutureListener getLoginFutureListener() {
        return loginFutureListener;
    }

    public RegisterFutureListener getRegisterFutureListener() {
        return registerFutureListener;
    }

    public PersonMsgFutureListener getPersonMsgFutureListener() {
        return personMsgFutureListener;
    }

    public FriendAddFutureListener getFriendAddFutureListener() {
        return friendAddFutureListener;
    }

    public FriendReplyFutureListener getFriendReplyFutureListener() {
        return friendReplyFutureListener;
    }

    public FriendRemoveFutureListener getFriendRemoveFutureListener() {
        return friendRemoveFutureListener;
    }

    public InfoUpdateFutureListener getInfoUpdateFutureListener() {
        return infoUpdateFutureListener;
    }

    public InfoLookFutureListener getInfoLookFutureListener() {
        return infoLookFutureListener;
    }

    public FriendInfoFutureListener getFriendInfoFutureListener() {
        return friendInfoFutureListener;
    }

    public AllFriendFutureListener getAllFriendFutureListener() {
        return allFriendFutureListener;
    }

    public GroupCreateFutureListener getGroupCreateFutureListener() {
        return groupCreateFutureListener;
    }

    public GroupDisbandFutureListener getGroupDisbandFutureListener() {
        return groupDisbandFutureListener;
    }

    public MemberInviteFutureListener getMemberInviteFutureListener() {
        return memberInviteFutureListener;
    }

    public MemberKickFutureListener getMemberKickFutureListener() {
        return memberKickFutureListener;
    }

    public AllGroupsFutureListener getAllGroupsFutureListener() {
        return allGroupsFutureListener;
    }

    public GroupMsgFutureListener getGroupMsgFutureListener() {
        return groupMsgFutureListener;
    }
}
