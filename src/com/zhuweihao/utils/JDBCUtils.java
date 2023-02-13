package com.zhuweihao.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

/**
 * @Author zhuweihao
 * @Date 2023/2/13 17:14
 * @Description com.zhuweihao.utils
 */
public class JDBCUtils {
    private static String DATABASE_DRIVER;
    private static String DATABASE_URL;
    private static String DATABASE_USER;
    private static String DATABASE_PASSWORD;

    // 初始化读取到相关信息
    static {
        try {
            Properties prop = new Properties();
            ClassLoader classLoader = JDBCUtils.class.getClassLoader();
            URL res = classLoader.getResource("db.properties");
            String path = res.getPath();
            prop.load(new FileReader(path));
            DATABASE_URL = prop.getProperty("DATABASE_URL");
            DATABASE_USER = prop.getProperty("DATABASE_USER");
            DATABASE_PASSWORD = prop.getProperty("DATABASE_PASSWORD");
            DATABASE_DRIVER = prop.getProperty("DATABASE_DRIVER");
            // 加载驱动
            Class.forName(DATABASE_DRIVER);
        } catch (ClassNotFoundException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @return Connection对象
     * @throws SQLException
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭连接
     *
     * @param connection
     * @param statement
     * @param resultSet
     * @throws SQLException
     */
    public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            //关闭connection
            if (connection != null) {
                connection.close();
            }
            //关闭statement
            if (statement != null) {
                statement.close();
            }
            //关闭结果集
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     *
     * @param connection
     * @param statement
     * @throws SQLException
     */
    public static void closeConnection(Connection connection, Statement statement) {
        try {
            //关闭connection
            if (connection != null) {
                connection.close();
            }
            //关闭statement
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
