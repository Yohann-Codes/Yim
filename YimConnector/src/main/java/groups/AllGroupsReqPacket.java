package groups;

import packet.PacketType;
import packet.Request;

/**
 * 查看讨论组所有成员请求数据包
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class AllGroupsReqPacket extends Request {
    public AllGroupsReqPacket(String username) {
        super(username);
        packetType = PacketType.ALL_GROUPS_REQ;
    }
}
