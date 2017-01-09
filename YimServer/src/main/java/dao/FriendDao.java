package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 好友数据访问层
 * <p>
 * Created by yohann on 2017/1/8.
 */
public class FriendDao extends Dao {

    /**
     * 调用父类构造方法连接数据库
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public FriendDao() throws SQLException, ClassNotFoundException {
        super();
    }

    /**
     * 向好友表中插入注册的用户名
     *
     * @param username
     * @return
     */
    public int insertUsername(String username) {
        String sql = "INSERT INTO friend_list (username) VALUES (?)";
        PreparedStatement pstmt = null;
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return row;
    }

    /**
     * 修改好友用户名连接串
     * <p>
     * 添加／删除都使用此方法
     *
     * @param username
     * @param friends
     * @return
     */
    public int updateFriend(String username, String friends) {
        String sql = "update friend_list set friends = ? where username = ?";
        PreparedStatement pstmt = null;
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, friends);
            pstmt.setString(2, username);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return row;
    }

    /**
     * 查询指定用户的好友
     *
     * @param username
     * @return 好友名之间以空格相连，形式为："xx xx xx"
     */
    public String queryFriends(String username) {
        String sql = "SELECT friends FROM friend_list WHERE username = ?";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String friends = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                friends = resultSet.getString("friends");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }
}
