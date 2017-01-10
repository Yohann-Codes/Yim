import interfaces.Login;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yohann on 2017/1/9.
 */
public class MyTest {
    @Test
    public void activeTest() {
        new Login("yohann", "456").execute();
    }
}
