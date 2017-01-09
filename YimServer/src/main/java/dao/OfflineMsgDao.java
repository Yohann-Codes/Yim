package dao;

import packet.ChatPacket;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 离线消息访问层
 * <p>
 * Created by yohann on 2017/1/8.
 */
public class OfflineMsgDao extends Dao {
    /**
     * 调用父类构造方法，连接MySQL数据库
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public OfflineMsgDao() throws ClassNotFoundException, SQLException {
        super();
    }

    /**
     * 插入一条离线消息
     *
     * @param sender
     * @param receiver
     * @param msg
     * @param time
     * @return
     */
    public int insertMsg(String sender, String receiver, String msg, Timestamp time) {
        String sql = "INSERT INTO offline_msg (sender, receiver, message, time) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = null;
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sender);
            pstmt.setString(2, receiver);
            pstmt.setString(3, msg);
            pstmt.setTimestamp(4, time);
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
     * 查询离线消息
     *
     * @param receiver
     * @return
     */
    public List<ChatPacket> queryMsg(String receiver) {
        List<ChatPacket> offlineMsgs = new ArrayList<ChatPacket>();
        String sql = "SELECT * FROM offline_msg WHERE receiver = ?";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, receiver);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                ChatPacket offlineMsg = new ChatPacket();
                offlineMsg.setSrcUsername(resultSet.getString("sender"));
                offlineMsg.setDesUsername(resultSet.getString("receiver"));
                offlineMsg.setMessage(resultSet.getString("message"));
                offlineMsg.setTime(resultSet.getTimestamp("time").getTime());
                offlineMsgs.add(offlineMsg);
            }
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
        return offlineMsgs;
    }

    /**
     * 删除离线消息
     *
     * @param receiver
     * @return
     */
    public int deleteMsg(String receiver) {
        String sql = "DELETE FROM offline_msg where receiver = ?";
        PreparedStatement pstmt = null;
        int row = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, receiver);
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
