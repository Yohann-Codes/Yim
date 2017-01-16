package account.register;

import account.OnConnectionListener;
import common.Constants;
import common.UserInfo;
import future.Future;
import transport.Service;

/**
 * 注册接口
 *
 * Future future = new Register(username, password).execute();
 * future.addListener(new RegisterFutureListener() {
 *     public void onFinishRegister(RegRespPacket regRespPacket) {
 *         if (regRespPacket.isSuccess()) {
 *             System.out.println("注册成功");
 *         } else {
 *             System.out.println("注册失败 " + regRespPacket.getHint());
 *         }
 *     }
 * });
 *
 * Created by yohann on 2017/1/16.
 */
public class Register implements OnConnectionListener {
    private String username;
    private String password;

    public Register(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 客户端调用此方法注册
     *
     * @return
     */
    public Future execute() {
        new Thread() {
            @Override
            public void run() {
                new Service(Register.this).connect(Constants.HOST, Constants.PORT);
            }
        }.start();

        return Constants.FUTURE;
    }

    public void onConnetionComplete() {
        System.out.println("连接成功 --> 开始注册");
        register();
    }

    /**
     * 注册
     */
    private void register() {
        RegReqPacket regReqPacket = new RegReqPacket(username, password);
        UserInfo.channel.writeAndFlush(regReqPacket);
    }
}
