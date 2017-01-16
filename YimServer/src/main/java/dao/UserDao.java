package dao;

import bean.UserBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据访问类
 * <p>
 * Created by yohann on 2017/1/14.
 */
public class UserDao extends Dao {

    /**
     * 连接MySQL数据库
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public UserDao() throws ClassNotFoundException, SQLException {
        super();
    }

    /**
     * 添加用户
     *
     * @param username
     * @param password
     * @return
     */
    public int insertUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("MySQL添加用户出现异常", e);
        }
        return row;
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    public List<UserBean> queryByUsername(String username) {
        List<UserBean> users = new ArrayList<UserBean>();
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                UserBean user = new UserBean();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setSex(resultSet.getString("sex"));
                user.setAge(resultSet.getString("age"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setIntroduction(resultSet.getString("introduction"));
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.warn("MySQL查询用户出现异常", e);
        }
        return users;
    }

    public void close() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.warn("MySQL关闭ResultSet出现异常", e);
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                LOGGER.warn("MySQL关闭PreparedStatement出现异常", e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.warn("MySQL关闭Connection出现异常", e);
            }
        }
    }
}
