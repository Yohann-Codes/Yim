package dao;

import friends.FriendAddReqPacket;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 好友申请信息访问类
 * <p>
 * Created by yohann on 2017/1/17.
 */
public class FriendReqDao extends Dao {
    /**
     * 连接MySQL数据库
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public FriendReqDao() throws ClassNotFoundException, SQLException {
        super();
    }

    /**
     * 添加申请好友请求信息
     *
     * @param requester
     * @param responser
     * @param info
     * @return
     */
    public int insertFriendReq(String requester, String responser, String info) {
        String sql = "INSERT INTO friend_add_req (requester, responser, info, result) VALUES (?, ?, ?, ?)";
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, requester);
            pstmt.setString(2, responser);
            pstmt.setString(3, info);
            pstmt.setString(4, "untreat");
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("MySQL添加申请好友信息出现异常", e);
        }
        return row;
    }

    /**
     * 删除请求信息
     *
     * @param requester
     * @param responser
     * @return
     */
    public int removeFriendReq(String requester, String responser) {
        String sql = "DELETE FROM friend_add_req where requester = ? and responser = ?";
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, requester);
            pstmt.setString(2, responser);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("MySQL删除好友请求信息出现异常", e);
        }
        return row;
    }

    /**
     * 修改好友请求信息
     *
     * @param requester
     * @param responser
     * @param result
     * @return
     */
    public int updateFriendReq(String requester, String responser, boolean result) {
        String sql = "update friend_add_req set result = ? where requester = ? and responser = ?";
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, result);
            pstmt.setString(2, requester);
            pstmt.setString(3, responser);
            row = pstmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.warn("MySQL修改好友请求信息出现异常", e);
        }
        return row;
    }

    /**
     * 查询未处理的请求
     *
     * @param responser
     * @return
     */
    public List<FriendAddReqPacket> queryUntreatReq(String responser) {
        List<FriendAddReqPacket> friendAddReqs = new ArrayList<FriendAddReqPacket>();
        String sql = "SELECT * FROM friend_add_req WHERE responser = ? and result = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, responser);
            pstmt.setString(2, "untreat");
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String requester = resultSet.getString("requester");
                String info = resultSet.getString("info");
                FriendAddReqPacket friendAddReqPacket =
                        new FriendAddReqPacket(requester, responser, info);
                friendAddReqs.add(friendAddReqPacket);
            }
        } catch (SQLException e) {
            LOGGER.warn("MySQL查询好友请求出现异常", e);
        }
        return friendAddReqs;
    }
}
