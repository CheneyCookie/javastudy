package com.cheney.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 操作JDBC的工具类，其中封装了一些工具方法
 * Verson1
 */
public class JDBCTools {
	/**
	 * 1.获取连接的方法
	 * 通过读取配置文件从数据库服务器获取一个连接
	 */
	public static Connection getConnection() throws Exception {
		// 1.准备连接数据库的四个属性
		// 1.1创建Properties对象
		Properties properties = new Properties();

		// 1.2获取jdbc.properties对应的输入流
		InputStream in = JDBCTools.class.getClassLoader()
				.getResourceAsStream("jdbc.properties");

		// 1.3加载对应的输入流
		properties.load(in);

		// 1.4具体决定user,password等4个字符串
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		String jdbcUrl = properties.getProperty("jdbcUrl");
		String driver = properties.getProperty("driver");

		// 2.加载数据库驱动(对应的Driver实现类中有注册驱动的静态代码块)，使用DriverManager可注册多个驱动
		Class.forName(driver);

		// 3.通过DriverManager的getConnection()方法获取数据库
		return DriverManager.getConnection(jdbcUrl, user, password);
	}
	
	/**
	 * 关闭Statement和Connection
	 */
	public static void release(Statement statement,Connection conn){
		if(statement!=null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭Statement和Connection以及ResultSet
	 */
	public static void release(ResultSet rs,Statement statement,Connection conn){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(statement!=null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
