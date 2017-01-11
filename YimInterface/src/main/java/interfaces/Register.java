package interfaces;

import common.CacheVars;
import common.Constants;
import packet.RegisterPacket;
import trasport.Service;

/**
 * 注册接口
 * <p>
 * Created by yohann on 2017/1/11.
 */
public class Register extends Connection {
    private String username;
    private String password;

    /**
     * 连接服务器
     */
    public Register(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 客户端调用此方法进行注册
     */
    public void execute() {
        service = new Service(this);
        service.connect(Constants.HOST, Constants.PORT);
    }

    @Override
    public void onFinishConnect() {
        System.out.println("连接成功 --> 开始注册");
        // 注册
        doRegister(username, password);
    }

    public void doRegister(String username, String password) {
        // 创建数据包
        RegisterPacket registerPacket = new RegisterPacket();
        registerPacket.setUsername(username);
        registerPacket.setPassword(password);
        // 发送数据包
        CacheVars.channel.writeAndFlush(registerPacket);
    }
}
