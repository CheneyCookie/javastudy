package com.cheney.jdbc4;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCTools {
	private static DataSource dataSource=null;
	
	//数据库连接池只被初始化一次
	static{
		dataSource=new ComboPooledDataSource("helloc3p0");
	}

	/*
	 * 获取数据库连接的方法
	 */
	public static Connection getConnection() throws IOException,
			ClassNotFoundException, SQLException {
		return dataSource.getConnection();
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
				/*
				 * 数据库连接池的Connection对象进行close时，并不是真的进行关闭，而是把该数据库连接归还到数据库连接池中
				 */
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
	
	/*
	 * 处理数据库事务的
	 * 提交事务
	 */
	public static void commit(Connection connection){
		if(connection!=null){
			try {
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//回滚事务
	public static void rollback(Connection connection){
		if(connection!=null){
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//开始事务
	public static void beginTx(Connection connection){
		if(connection !=null){
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
