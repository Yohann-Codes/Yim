package logic;

import dao.UserDao;
import io.netty.channel.ChannelHandlerContext;
import packet.LoginPacket;
import packet.RespPacket;
import packet.UserInfoPacket;

import java.sql.SQLException;
import java.util.List;

/**
 * 登录逻辑处理
 * <p>
 * Created by yohann on 2017/1/9.
 */
public class LoginLogic {
    private LoginPacket loginPacket;
    private ChannelHandlerContext ctx;

    public LoginLogic(LoginPacket loginPacket, ChannelHandlerContext ctx) {
        this.loginPacket = loginPacket;
        this.ctx = ctx;
    }

    public void deal() {
        RespPacket respPacket = new RespPacket();
        try {
            UserDao userDao = new UserDao();
            List<UserInfoPacket> users = userDao.queryByUsername(loginPacket.getUsername());
            if (users.size() > 0) {
                if (loginPacket.getPassword().equals(users.get(0).getPassword())) {
                    // 登录成功
                    respPacket.setSuccessful(true);
                    System.out.println("登录成功");
                } else {
                    // 登录失败
                    respPacket.setSuccessful(false);
                    System.out.println("登录失败");
                }
            } else {
                // 登录失败
                respPacket.setSuccessful(false);
                System.out.println("登录失败");
            }
            ctx.writeAndFlush(respPacket);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
