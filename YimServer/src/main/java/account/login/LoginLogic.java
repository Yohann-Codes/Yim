package account.login;

import bean.UserBean;
import connection.ConnPool;
import connection.TokenFactory;
import connection.TokenPool;
import dao.UserDao;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.ReferenceCountUtil;
import org.apache.log4j.Logger;
import transport.HeartbeatHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 登录逻辑
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class LoginLogic {
    private static final Logger LOGGER = Logger.getLogger(LoginLogic.class);

    private LoginReqPacket loginReqPacket;
    private Channel channel;
    private String username;

    public LoginLogic(LoginReqPacket loginReqPacket, Channel channel) {
        this.loginReqPacket = loginReqPacket;
        this.channel = channel;
    }

    /**
     * 登录信息验证
     */
    public void deal() {
        username = loginReqPacket.getUsername();
        String password = loginReqPacket.getPassword();
        LOGGER.debug("username=" + username + " password=" + password);
        try {
            UserDao userDao = new UserDao();
            List<UserBean> users = userDao.queryByUsername(username);
            // 关闭数据库资源
            userDao.close();
            if (users.size() == 1) {
                if (password.equals(users.get(0).getPassword())) {
                    // 成功
                    success();
                } else {
                    // 失败，密码错误
                    defeat("密码错误");
                }
            } else {
                // 失败，用户名错误
                defeat("用户名错误");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("MySQL连接异常", e);
        } catch (SQLException e) {
            LOGGER.warn("MySQL连接异常", e);
        } finally {
            ReferenceCountUtil.release(loginReqPacket);
        }
    }

    /**
     * 信息验证成功
     */
    private void success() {
        // 生成token
        final TokenFactory factory = new TokenFactory();
        final Long token = factory.generate();
        // 维护连接
        boolean b1 = ConnPool.add(username, channel);
        // 维护token
        boolean b2 = TokenPool.add(token);
        if (b1 && b2) {
            // 发送响应数据包
            LoginRespPacket loginRespPacket = new LoginRespPacket(username, true, token, null);
            ChannelFuture future = channel.writeAndFlush(loginRespPacket);
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        LOGGER.info(username + " 登录成功");
                        // 开启心跳检测
                        LOGGER.info(username + " 开启心跳检测");
                        channel.pipeline().addAfter("IdleStateHandler",
                                "HeartbeatHandler", new HeartbeatHandler(channel));
                        // 发送离线消息和通知

                    } else {
                        LOGGER.warn(username + " 登录成功响应包发送失败");
                        ConnPool.remove(username);
                        TokenPool.remove(token);
                    }
                }
            });
        } else {
            defeat("服务器内部错误");
        }
    }

    /**
     * 信息验证失败
     *
     * @param hint
     */
    private void defeat(String hint) {
        // 发送响应数据包
        LoginRespPacket loginRespPacket = new LoginRespPacket(username, false, null, hint);
        ChannelFuture future = channel.writeAndFlush(loginRespPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    LOGGER.info(username + " 登录失败");
                    channel.close();
                } else {
                    LOGGER.warn(username + " 登录失败响应包发送失败");
                }
            }
        });
    }
}
