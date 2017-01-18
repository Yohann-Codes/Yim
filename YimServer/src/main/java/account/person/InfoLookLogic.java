package account.person;

import bean.UserBean;
import dao.UserDao;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * 查看个人信息逻辑
 * <p>
 * Created by yohann on 2017/1/18.
 */
public class InfoLookLogic {
    private static final Logger LOGGER = Logger.getLogger(InfoLookLogic.class);

    private InfoLookReqPacket infoLookReqPacket;
    private Channel channel;

    public InfoLookLogic(InfoLookReqPacket infoLookReqPacket, Channel channel) {
        this.infoLookReqPacket = infoLookReqPacket;
        this.channel = channel;
    }

    public void deal() {
        UserDao userDao = null;
        String username = infoLookReqPacket.getUsername();
        try {
            userDao = new UserDao();
            List<UserBean> users = userDao.queryByUsername(username);
            if (users.size() == 1) {
                UserBean userBean = users.get(0);
                success(userBean);
            } else {
                // 数据库异常
                defeat("数据库异常");
                LOGGER.warn("查询个人信息 数据库异常");
            }
        } catch (ClassNotFoundException e) {
            LOGGER.warn("数据库连接异常");
        } catch (SQLException e) {
            LOGGER.warn("数据库连接异常");
        } finally {
            if (userDao != null) {
                userDao.close();
            }
        }
    }

    private void success(final UserBean userBean) {
        InfoLookRespPacket infoLookRespPacket =
                new InfoLookRespPacket(true, null,
                        userBean.getUsername(),
                        userBean.getName(),
                        userBean.getSex(),
                        userBean.getAge(),
                        userBean.getPhone(),
                        userBean.getAddress(),
                        userBean.getIntroduction());
        ChannelFuture future = channel.writeAndFlush(infoLookRespPacket);
        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                LOGGER.info(userBean.getUsername() + " 查询个人信息 成功");
            }
        });
    }

    private void defeat(String hint) {
        channel.writeAndFlush(new InfoLookRespPacket(false, hint,
                null, null, null, null, null, null, null));
    }
}
