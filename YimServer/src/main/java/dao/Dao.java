package dao;

import common.Config;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Properties;

/**
 * 数据访问层
 * <p>
 * Created by yohann on 2017/1/8.
 */
public abstract class Dao {
    public static final Logger LOGGER = Logger.getLogger(Dao.class);

    //需要关闭的资源
    protected Connection conn;
    protected PreparedStatement pstmt;
    protected ResultSet resultSet;

    /**
     * 连接MySQL数据库
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Dao() throws ClassNotFoundException, SQLException {
        //加载JDBC驱动
        Class.forName("com.mysql.jdbc.Driver");

        //准备数据库连接数据
        Properties info = new Properties();
        String url = Config.DB_URL;
        info.put("user", Config.DB_USERNAME);
        info.put("password", Config.DB_PASSWORD);

        //获取连接对象
        conn = DriverManager.getConnection(url, info);
    }

    /**
     * 关闭数据库资源
     */
    public abstract void close();
}
