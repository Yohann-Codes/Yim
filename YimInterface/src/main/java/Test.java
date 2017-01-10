import interfaces.Login;
import interfaces.Logout;

import java.util.Scanner;

/**
 * Created by yohann on 2017/1/10.
 */
public class Test {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                Scanner sc = new Scanner(System.in);
                while (sc.hasNextLine()) {
                    String command = sc.nextLine();
                    String[] commandArr = command.split(" ");

                    System.out.println(commandArr[0]);

                    if ("login".equals(commandArr[0])) {
                        new Login(commandArr[1], commandArr[2]).execute();
                    }
                    if ("logout".equals(commandArr[0])) {
                        new Logout().execute();
                    }
                }
            }
        }.start();
    }
}