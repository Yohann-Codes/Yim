import interfaces.Login;
import interfaces.Logout;
import interfaces.Register;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by yohann on 2017/1/10.
 */
public class Test {
    public static String[] cArr;

    public static void main(String[] args) {
        Map<String, Integer> cMap = new HashMap<String, Integer>();
        cMap.put("login", 1);
        cMap.put("logout", 2);
        cMap.put("register", 3);

        Scanner sc = new Scanner(System.in);
        while (true) {
            if (sc.hasNextLine()) {
                String command = sc.nextLine();
                cArr = command.split(" ");
                switch (cMap.get(cArr[0])) {
                    case 1:
                        new Thread() {
                            @Override
                            public void run() {
                                new Login(cArr[1], cArr[2]).execute();
                            }
                        }.start();
                        break;

                    case 2:
                        new Logout().execute();
                        break;

                    case 3:
                        new Register(cArr[1], cArr[2]).execute();
                        break;
                }
            }
        }
    }
}