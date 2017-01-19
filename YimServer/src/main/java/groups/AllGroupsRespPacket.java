package groups;

import packet.PacketType;
import packet.Response;

import java.util.List;
import java.util.Map;

/**
 * 全部讨论组以及各自的成员
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class AllGroupsRespPacket extends Response {

    private Map<String, List<String>> groups;

    public AllGroupsRespPacket(boolean isSuccess, String hint, Map<String, List<String>> groups) {
        super(isSuccess, hint);
        packetType = PacketType.ALL_GROUPS_RESP;
        this.groups = groups;
    }

    public Map<String, List<String>> getGroups() {
        return groups;
    }
}
