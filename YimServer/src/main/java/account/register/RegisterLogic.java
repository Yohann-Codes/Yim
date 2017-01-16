package account.register;

import bean.UserBean;
import dao.UserDao;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * 注册逻辑
 * <p>
 * Created by yohann on 2017/1/16.
 */
public class RegisterLogic {
    private static final Logger LOGGER = Logger.getLogger(RegisterLogic.class);

    private RegReqPacket regReqPacket;
    private Channel channel;
    private String username;

    public RegisterLogic(RegReqPacket regReqPacket, Channel channel) {
        this.regReqPacket = regReqPacket;
        this.channel = channel;
    }

    /**
     * 注册信息验证
     */
    public void deal() {
        username = regReqPacket.getUsername();
        // 查询用户名是否已存在
        try {
            UserDao userDao = new UserDao();
            List<UserBean> users = userDao.queryByUsername(username);
            if (users.size() == 0) {
                // 添加用户
                int row = userDao.insertUser(username, regReqPacket.getPassword());
                if (row == 1) {
                    // 成功
                    success();
                } else {
                    // 失败，数据库错误
                    defeat("数据库错误");
                    LOGGER.warn("注册时数据库出现错误");
                }
            } else {
                // 失败，用户名已存在
                defeat("用户名已存在");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("MySQL连接异常", e);
        } catch (SQLException e) {
            LOGGER.warn("MySQL连接异常", e);
        }
    }

    /**
     * 信息验证成功
     */
    private void success() {
        RegRespPacket regRespPacket = new RegRespPacket(true, null);
        ChannelFuture future = channel.writeAndFlush(regRespPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    // 关闭连接
                    channel.close().sync();
                    LOGGER.info(username + " 注册成功");
                } else {
                    LOGGER.warn(username + " 注册成功响应包发送失败");
                }
            }
        });
    }

    /**
     * 信息验证失败
     *
     * @param hint 错误信息
     */
    private void defeat(String hint) {
        RegRespPacket regRespPacket = new RegRespPacket(false, hint);
        ChannelFuture future = channel.writeAndFlush(regRespPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    // 关闭连接
                    channel.close().sync();
                    LOGGER.info(username + " 注册失败");
                } else {
                    LOGGER.warn(username + " 注册失败响应包发送失败");
                }
            }
        });
    }
}
