package dispatch;

import account.login.LoginRespPacket;
import account.register.RegRespPacket;
import common.Constants;
import common.UserInfo;
import future.LoginFutureListener;
import future.RegisterFutureListener;
import packet.HeartbeatPacket;
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
                finishLogin(loginRespPacket);
                LoginFutureListener loginFutureListener = Constants.FUTURE.getLoginFutureListener();
                loginFutureListener.onFinishLogin(loginRespPacket);
                break;

            case PacketType.REGISTER_RESP:
                RegRespPacket regRespPacket = (RegRespPacket) packet;
                RegisterFutureListener registerFutureListener = Constants.FUTURE.getRegisterFutureListener();
                registerFutureListener.onFinishRegister(regRespPacket);
                break;
        }
    }

    /**
     * 登录成功后续工作
     *
     * @param loginRespPacket
     */
    public void finishLogin(LoginRespPacket loginRespPacket) {
        if (loginRespPacket.isSuccess()) {
            // 保存用户信息
            UserInfo.username = loginRespPacket.getUsername();
            UserInfo.token = loginRespPacket.getToken();

            UserInfo.channel.writeAndFlush(new HeartbeatPacket("yanghuan"));

            // 启动心跳
            UserInfo.channel.pipeline().addAfter("IdleStateHandler",
                    "HeartbeatHandler", new HeartbeatHandler(UserInfo.username));
        }
    }
}
