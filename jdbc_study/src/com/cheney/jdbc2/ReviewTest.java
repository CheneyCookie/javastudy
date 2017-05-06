package com.cheney.jdbc2;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;

public class ReviewTest {
	
	/**
	 * Connection代表应用程序和数据库的一个连接
	 */

	@Test
	public void testGetConnection() throws Exception {
		//1.准备获取连接的四个字符串:user password jdbcUrl driverClass
		String user ="root";
		String password="root";
		String jdbcUrl="jdbc:mysql:///test";
		String driverClass="com.mysql.jdbc.Driver";
		
		//2.加载驱动：Class.forName(driverClass)
		Class.forName(driverClass);
		
		//3.调用DriverManager.getConnection(jdbcUrl,user,password)获取数据库连接
		Connection connection=DriverManager.getConnection(jdbcUrl,user,password);
		System.out.println(connection);
		
	}
	
	@Test
	public void testGetConnection2() throws Exception{
		Connection connection = getConnection();
		System.out.println(connection);
	}

	public Connection getConnection() throws IOException,
			ClassNotFoundException, SQLException {
		//0.读取jdbc.properties
			//1.属性文件对应Java中的Properties类
			//2.可以使用类加载器加载bin目录（类路径下）的文件
		Properties properties=new Properties();
		InputStream in=ReviewTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
		properties.load(in);
		
		//1.准备获取连接的四个字符串:user password jdbcUrl driverClass
		String user =properties.getProperty("user");
		String password=properties.getProperty("password");
		String jdbcUrl=properties.getProperty("jdbcUrl");
		String driverClass=properties.getProperty("driver");
		
		//2.加载驱动：Class.forName(driverClass)
		Class.forName(driverClass);
		
		//3.调用DriverManager.getConnection(jdbcUrl,user,password)获取数据库连接
		Connection connection=DriverManager.getConnection(jdbcUrl,user,password);
		return connection;
	}
	
	public void releaseDB(ResultSet resultSet,Statement statement,Connection connection){
		if(resultSet!=null){
			try {
				resultSet.close();
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
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 1.Statement是用于操作Sql的对象
	 */
	@Test
	public void testStatement(){
		Connection connection=null;
		Statement statement=null;
		try {
			//1.获取数据库连接
			connection=getConnection();
			//2.调用Connection对象的createStatement()方法获取Statement对象
			statement=connection.createStatement();
			//3.准备SQL语句
			String sql="UPDATE customers SET name='Jerry'"+"WHERE id=3";
			//4.发送SQL语句：调用Statement对象的executeUpdate(sql)方法
			statement.executeUpdate(sql);
		} catch (Exception e) {
		}finally{
			releaseDB(null, statement, connection);
		}
		
		
		
		//5.关闭数据库资源:由里向外关
	}
	
	/**
	 * ResultSet封装JDBC的查询结果
	 */
	@Test
	public void testResultSet(){
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		
		try {
			//1.获取数据库连接
			connection=getConnection();
			
			//2.调用Connection对象的createStatement()方法获取Statement对象
			statement=connection.createStatement();
			
			//3.准备SQL语句
			String sql="SELECT id,name,email,birth FROM customers";
			//4.发送SQL语句：调用Statement对象的executeQuery(sql)方法
			//得到结果集对象ResultSet
			resultSet=statement.executeQuery(sql);
			
			//5.处理结果集
			//1.调用ResultSet的next()方法：查看结果集的下一条记录是否有效，若有效则下移指针
			//2.getXxx方法获取具体列的值
			while(resultSet.next()){
				int id=resultSet.getInt(1);
				String name=resultSet.getString(2);
				String email=resultSet.getString(3);
				Date birth=resultSet.getDate(4);
				
				System.out.println(id);
				System.out.println(name);
				System.out.println(email);
				System.out.println(birth);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//6.关闭数据库资源
			releaseDB(resultSet, statement, connection);
		}
		
	}

}
