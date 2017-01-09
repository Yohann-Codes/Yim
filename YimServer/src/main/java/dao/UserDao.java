package dao;

import packet.UserInfoPacket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据访问层
 * <p>
 * Created by yohann on 2017/1/8.
 */
public class UserDao extends Dao {

    /**
     * 调用父类构造方法连接数据库
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public UserDao() throws SQLException, ClassNotFoundException {
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
        String sql = "INSERT INTO user_info (username, password) VALUES (?, ?)";
        PreparedStatement pstmt = null;
        int row = 0;

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
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
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    public List<UserInfoPacket> queryByUsername(String username) {
        List<UserInfoPacket> users = new ArrayList<UserInfoPacket>();
        String sql = "SELECT * FROM user_info WHERE username = ?";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                UserInfoPacket user = new UserInfoPacket();
                user.setUsername(resultSet.getString("username"));
                user.setName(resultSet.getString("name"));
                user.setSex(resultSet.getString("sex"));
                user.setAge(resultSet.getString("age"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setIntroduction(resultSet.getString("introduction"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    /**
     * 修改姓名
     *
     * @param username
     * @param name
     * @return
     */
    public int updateName(String username, String name) {
        String sql = "update user_info set name = ? where username = ?";
        PreparedStatement pstmt = null;
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
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
     * 修改密码
     *
     * @param username
     * @param password
     * @return
     */
    public int updatePassword(String username, String password) {
        String sql = "update user_info set password = ? where username = ?";
        PreparedStatement pstmt = null;
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, password);
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
     * 修改性别
     *
     * @param username
     * @param sex
     * @return
     */
    public int updateSex(String username, String sex) {
        String sql = "update user_info set sex = ? where username = ?";
        PreparedStatement pstmt = null;
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sex);
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
     * 修改年龄
     *
     * @param username
     * @param age
     * @return
     */
    public int updateAge(String username, String age) {
        String sql = "update user_info set age = ? where username = ?";
        PreparedStatement pstmt = null;
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, age);
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
     * 修改联系电话
     *
     * @param username
     * @param phone
     * @return
     */
    public int updatePhone(String username, String phone) {
        String sql = "update user_info set phone = ? where username = ?";
        PreparedStatement pstmt = null;
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, phone);
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
     * 修改地址
     *
     * @param username
     * @param address
     * @return
     */
    public int updateAddress(String username, String address) {
        String sql = "update user_info set address = ? where username = ?";
        PreparedStatement pstmt = null;
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, address);
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
     * 修改自我介绍
     *
     * @param username
     * @param introduction
     * @return
     */
    public int updateIntroduction(String username, String introduction) {
        String sql = "update user_info set introduction = ? where username = ?";
        PreparedStatement pstmt = null;
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, introduction);
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
}
