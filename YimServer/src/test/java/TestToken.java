import connection.TokenFactory;
import connection.TokenPool;
import org.junit.Test;

/**
 * Created by yohann on 2017/1/15.
 */
public class TestToken {
    @Test
    public void tokenTest() {
        TokenFactory factory = new TokenFactory();
        Long token = factory.generate();
        TokenPool.add(token);
        System.out.println(token);
        System.out.println(TokenPool.query(token));
    }
}
