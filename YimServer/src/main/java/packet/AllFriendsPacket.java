package packet;

import common.Packet;
import common.PacketType;

import java.util.List;

/**
 * 已添加的好友
 *
 * Created by yohann on 2017/1/8.
 */
public class AllFriendsPacket extends Packet {

    private String username;
    private List<String> friends;

    public AllFriendsPacket() {
        packetType = PacketType.ALL_FRIEND;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
