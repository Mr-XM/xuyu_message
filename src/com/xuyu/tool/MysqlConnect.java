package com.xuyu.tool;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * 数据库连接操作与断开操作
 */
public class MysqlConnect {
    public static final String url = "jdbc:MySQL://localhost:3306/xuyujiajiao?characterEncoding=utf8&useSSL=true";
    //public static final String name = "com.mysql.cj.jdbc.Driver";
    public static final String name = "com.mysql.jdbc.Driver";
    Connection con;

    public Connection getConnect() {
        try {
            Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url, "root", "4230980MENG");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public Connection closeConnect() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
    /**public static void main(String[] args)
     {
     MysqlConnect c=new MysqlConnect();
     c.getConnect();
     c.closeConnect();
     }*/
}
