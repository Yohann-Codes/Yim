package account.person;

import dao.UserDao;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import java.sql.SQLException;

/**
 * 修改个人信息逻辑
 * <p>
 * Created by yohann on 2017/1/18.
 */
public class InfoUpdateLogic {
    private static final Logger LOGGER = Logger.getLogger(InfoUpdateLogic.class);

    private InfoUpdateReqPacket infoUpdateReqPacket;
    private Channel channel;

    public InfoUpdateLogic(InfoUpdateReqPacket infoUpdateReqPacket, Channel channel) {
        this.infoUpdateReqPacket = infoUpdateReqPacket;
        this.channel = channel;
    }

    public void deal() {
        String username = infoUpdateReqPacket.getUsername();
        String password = infoUpdateReqPacket.getPassword();
        String name = infoUpdateReqPacket.getName();
        String sex = infoUpdateReqPacket.getSex();
        String age = infoUpdateReqPacket.getAge();
        String phone = infoUpdateReqPacket.getPhone();
        String address = infoUpdateReqPacket.getAddress();
        String introduction = infoUpdateReqPacket.getIntroduction();

        int r1 = 1, r2 = 1, r3 = 1, r4 = 1, r5 = 1, r6 = 1, r7 = 1;

        UserDao userDao = null;

        try {
            userDao = new UserDao();

            if (password != null) {
                // 修改密码
                r1 = userDao.updatePassword(username, password);
            }
            if (name != null) {
                // 修改姓名
                r2 = userDao.updateName(username, name);
            }
            if (sex != null) {
                r3 = userDao.updateSex(username, sex);
            }
            if (age != null) {
                r4 = userDao.updateAge(username, age);
            }
            if (phone != null) {
                r5 = userDao.updatePhone(username, phone);
            }
            if (address != null) {
                r6 = userDao.updateAddress(username, address);
            }
            if (introduction != null) {
                r7 = userDao.updateIntroduction(username, introduction);
            }

            // 发送响应
            if (r1 + r2 + r3 + r4 + r5 + r6 + r7 == 7) {
                LOGGER.info(username + " 修改信息 成功");
                success();
            } else {
                LOGGER.warn(username + " 修改信息 失败（数据库错误）");
                defeat("数据库错误");
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

    private void success() {
        InfoUpdateRespPacket infoUpdateRespPacket = new InfoUpdateRespPacket(true, null);
        channel.writeAndFlush(infoUpdateRespPacket);
    }

    private void defeat(String hint) {
        InfoUpdateRespPacket infoUpdateRespPacket = new InfoUpdateRespPacket(false, hint);
        channel.writeAndFlush(infoUpdateRespPacket);
    }
}
