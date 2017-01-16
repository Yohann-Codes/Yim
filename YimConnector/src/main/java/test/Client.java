package test;

import account.login.Login;
import account.login.LoginRespPacket;
import account.logout.Logout;
import account.register.RegRespPacket;
import account.register.Register;
import common.UserInfo;
import future.Future;
import future.LoginFutureListener;
import future.RegisterFutureListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 简单的测试Client
 * <p>
 * Created by yohann on 2017/1/15.
 */
public class Client {
    public static String[] cArr;

    public static void main(String[] args) {
        Map<String, Integer> cMap = new HashMap<String, Integer>();
        cMap.put("login", 1);
        cMap.put("logout", 2);
        cMap.put("register", 3);
        cMap.put("send", 4);

        Scanner sc = new Scanner(System.in);
        while (true) {
            if (sc.hasNextLine()) {
                String command = sc.nextLine();
                cArr = command.split(" ");
                switch (cMap.get(cArr[0])) {
                    case 1:
                        login();
                        break;
                    case 2:
                        new Logout().execute();
                        break;
                    case 3:
                        register();
                        break;
                }
            }
        }
    }

    private static void register() {
        Future future = new Register(cArr[1], cArr[2]).execute();
        future.addListener(new RegisterFutureListener() {
            public void onFinishRegister(RegRespPacket regRespPacket) {
                if (regRespPacket.isSuccess()) {
                    System.out.println("注册成功");
                } else {
                    System.out.println("注册失败 " + regRespPacket.getHint());
                }
            }
        });
    }

    public static void login() {
        Future future = new Login(cArr[1], cArr[2]).execute();
        future.addListener(new LoginFutureListener() {
            public void onFinishLogin(LoginRespPacket packet) {
                if (packet.isSuccess()) {
                    System.out.println("登录成功 ");
                    System.out.println("username=" + UserInfo.username);
                    System.out.println("channel=" + UserInfo.channel);
                    System.out.println("token=" + UserInfo.token);
                } else {
                    System.out.println("登录失败 " + packet.getHint());
                }
            }
        });
    }
}
