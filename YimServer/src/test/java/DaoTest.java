import dao.FriendDao;
import dao.OfflineMsgDao;
import dao.UserDao;
import org.junit.Test;
import packet.ChatPacket;
import packet.UserInfoPacket;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by yohann on 2017/1/8.
 */
public class DaoTest {
    @Test
    public void insertUser() {
        try {
            UserDao userDao = new UserDao();
            int rows = userDao.insertUser("yanghuan", "123");
            System.out.println(rows);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryByUsername() {
        try {
            UserDao userDao = new UserDao();
            List<UserInfoPacket> users = userDao.queryByUsername("yohann");
            System.out.println(users);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateName() {
        try {
            UserDao userDao = new UserDao();
            int row = userDao.updateName("yohann", "杨欢");
            System.out.println(row);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updatePassword() {
        try {
            UserDao userDao = new UserDao();
            int row = userDao.updatePassword("yohann", "456");
            System.out.println(row);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateSex() {
        try {
            UserDao userDao = new UserDao();
            int row = userDao.updateSex("yohann", "男");
            System.out.println(row);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateAge() {
        try {
            UserDao userDao = new UserDao();
            int row = userDao.updateAge("yohann", "20");
            System.out.println(row);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updatePhone() {
        try {
            UserDao userDao = new UserDao();
            int row = userDao.updatePhone("yohann", "18395403366");
            System.out.println(row);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateAddress() {
        try {
            UserDao userDao = new UserDao();
            int row = userDao.updateAddress("yohann", "宝鸡");
            System.out.println(row);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateIntroduction() {
        try {
            UserDao userDao = new UserDao();
            int row = userDao.updateIntroduction("yohann", "我最帅！！！");
            System.out.println(row);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertUsername() {
        try {
            FriendDao friendDao = new FriendDao();
            int row = friendDao.insertUsername("albert");
            System.out.println(row);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryFriends() {
        try {
            FriendDao friendDao = new FriendDao();
            String friends = friendDao.queryFriends("yohann");
            System.out.println(friends);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateFriend() {
        try {
            FriendDao friendDao = new FriendDao();
            int row = friendDao.updateFriend("yohann", "fri4");
            System.out.println(row);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertMsg() {
        try {
            OfflineMsgDao offlineMsgDao = new OfflineMsgDao();
            int row = offlineMsgDao.insertMsg("wangwu", "lisi", "在哪呢？",
                    new Timestamp(System.currentTimeMillis()));
            System.out.println(row);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void queryMsg() {
        try {
            OfflineMsgDao offlineMsgDao = new OfflineMsgDao();
            List<ChatPacket> msgs = offlineMsgDao.queryMsg("lisi");
            for (ChatPacket packet : msgs) {
                System.out.println(packet);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteMsg() {
        try {
            OfflineMsgDao offlineMsgDao = new OfflineMsgDao();
            int row = offlineMsgDao.deleteMsg("lisi");
            System.out.println(row);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
