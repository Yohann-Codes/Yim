package test;

import account.login.Login;
import account.logout.Logout;
import account.register.Register;
import future.Future;
import future.LoginFutureListener;
import future.PersonMsgFutureListener;
import future.RegisterFutureListener;
import message.person.PersonMsg;

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
        // 注册消息接收器
        Future.getFuture().addReceiver(new MyReceiver());

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
                    case 4:
                        sendPersonMsg();
                        break;
                }
            }
        }
    }

    private static void sendPersonMsg() {
        Future future = new PersonMsg(cArr[1], cArr[2]).execute();
        future.addListener(new PersonMsgFutureListener() {
            public void onSuccess() {
                System.out.println("发送成功");
            }

            public void onFailure(String hint) {
                System.out.println("发送失败，错误提示：" + hint);
            }
        });
    }

    private static void register() {
        Future future = new Register(cArr[1], cArr[2]).execute();
        future.addListener(new RegisterFutureListener() {
            public void onSuccess() {
                System.out.println("注册成功");
            }

            public void onFailure(String hint) {
                System.out.println("注册失败，错误提示：" + hint);
            }
        });
    }

    public static void login() {
        Future future = new Login(cArr[1], cArr[2]).execute();
        future.addListener(new LoginFutureListener() {
            public void onSuccess() {
                System.out.println("登录成功");
            }

            public void onFailure(String hint) {
                System.out.println("登录失败，错误提示：" + hint);
            }
        });
    }
}
