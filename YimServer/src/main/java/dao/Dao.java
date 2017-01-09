package dao;

import common.DBConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据访问层
 * <p>
 * Created by yohann on 2017/1/8.
 */
public class Dao {

    //数据库连接对象
    public Connection conn;

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
        String url = DBConstants.URL;
        info.put("user", DBConstants.USERNAME);
        info.put("password", DBConstants.PASSWORD);

        //获取连接对象
        conn = DriverManager.getConnection(url, info);
    }
}
