package com.cheney.jdbc3;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTools {

	/*
	 * 获取数据库连接的方法
	 */
	public static Connection getConnection() throws IOException,
			ClassNotFoundException, SQLException {
		// 0.读取jdbc.properties
		// 1.属性文件对应Java中的Properties类
		// 2.可以使用类加载器加载bin目录（类路径下）的文件
		Properties properties = new Properties();
		InputStream in = Student.class.getClassLoader().getResourceAsStream(
				"jdbc.properties");
		properties.load(in);

		// 1.准备获取连接的四个字符串:user password jdbcUrl driverClass
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		String jdbcUrl = properties.getProperty("jdbcUrl");
		String driverClass = properties.getProperty("driver");

		// 2.加载驱动：Class.forName(driverClass)
		Class.forName(driverClass);

		// 3.调用DriverManager.getConnection(jdbcUrl,user,password)获取数据库连接
		Connection connection = DriverManager.getConnection(jdbcUrl, user,
				password);
		return connection;
	}

	/*
	 * 释放数据库资源
	 */

	public static void releaseDB(ResultSet resultSet, Statement statement,
			Connection connection) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * 执行SQL的方法，SQL=insert,update或delete,而不包含select
	 */
	public static void update(String sql){
		Connection connection=null;
		Statement statement=null;
		try {
			//1.获取数据库连接
			connection=getConnection();
			//2.调用Connection对象的createStatement()方法获取Statement对象
			statement=connection.createStatement();
			//4.发送SQL语句：调用Statement对象的executeUpdate(sql)方法
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseDB(null, statement, connection);
		}
	}
	
	/*
	 * 执行sql语句,使用preparedStatement
	 * args,填写SQL占位符的可变参数
	 */
	public static void update(String sql,Object ...args){
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		try {
			connection=getConnection();
			preparedStatement=connection.prepareStatement(sql);
			
			for(int i=0;i<args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			releaseDB(null, preparedStatement, connection);
		}
	}
}
