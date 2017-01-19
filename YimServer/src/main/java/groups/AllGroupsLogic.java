package groups;

import dao.GroupDao;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 查看所在全部讨论组及讨论组的成员逻辑
 * <p>
 * Created by yohann on 2017/1/19.
 */
public class AllGroupsLogic {
    private static final Logger LOGGER = Logger.getLogger(AllGroupsLogic.class);

    private String username;
    private Channel channel;

    public AllGroupsLogic(AllGroupsReqPacket allGroupsReqPacket, Channel channel) {
        username = allGroupsReqPacket.getUsername();
        this.channel = channel;
    }

    public void deal() {
        GroupDao groupDao = null;
        try {
            groupDao = new GroupDao();
            Map<String, List<String>> groups = groupDao.queryAllbyMember(username);
            channel.writeAndFlush(new AllGroupsRespPacket(true, null, groups));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
