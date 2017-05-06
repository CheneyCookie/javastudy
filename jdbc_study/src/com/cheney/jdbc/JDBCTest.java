package com.cheney.jdbc;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;

public class JDBCTest {

	/*
	 * Driver是一个接口:数据库厂商必须提供实现的接口，能从其中获取数据库连接
	 * */
	@Test
	public void testDriver() throws SQLException {
		Driver driver=new com.mysql.jdbc.Driver();
		
		String url="jdbc:mysql://127.0.0.1:3306/test";
		Properties info=new Properties();
		info.put("user", "root");
		info.put("password", "root");
		Connection connection=driver.connect(url, info);
		System.out.println(connection);
	}
	
	/*
	 * 编写一个通用的方法，在不修改源程序的情况下，可以获取任何数据库的连接
	 * 解决方案：把数据库驱动Driver实现类的全类名、url、user、password放入一个
	 * 配置文件中，通过修改配置文件的方法实现和具体的数据库解耦
	 * */
	public Connection getConnection() throws Exception{
		String driverClass=null;
		String jdbcUrl=null;
		String user=null;
		String password=null;
		
		//读取类路径下的jdbc.properties文件
		InputStream in=getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties=new Properties();
		properties.load(in);
		driverClass=properties.getProperty("driver");
		jdbcUrl=properties.getProperty("jdbcUrl");
		user=properties.getProperty("user");
		password=properties.getProperty("password");
		
		Driver driver=(Driver) Class.forName(driverClass).newInstance();
		
		Properties info=new Properties();
		info.put("user", user);
		info.put("password", password);
		Connection connection=driver.connect(jdbcUrl, info);
		return connection;
	}
	
	@Test
	public void testGetConnection() throws Exception{
		System.out.println(getConnection());
	}
	
	/*
	 * DriverManager是驱动管理类
	 * 1.可以通过重载的getConnection()方法获取数据库连接，较为方便
	 * 2.可以同时管理多个驱动程序：若注册了多个数据库连接，则调用getConnection()方法时传入的参数不同，即返回不同的数据库连接
	 */
	@Test
	public void testDriverManager() throws Exception{
		//1.准备连接数据库的4个信息
		//驱动全类名
		String driverClass=null;
		//JDBC URL
		String jdbcUrl=null;
		//user
		String user=null;
		//password
		String password=null;
		
		//读取类路径下的jdbc.properties文件
		InputStream in=getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties=new Properties();
		properties.load(in);
		driverClass=properties.getProperty("driver");
		jdbcUrl=properties.getProperty("jdbcUrl");
		user=properties.getProperty("user");
		password=properties.getProperty("password");
		
		//2.加载数据库驱动程序(对应的Driver实现类中有注册驱动的静态代码块)，使用DriverManager可注册多个驱动
//		DriverManager.registerDriver(Class.forName(driverClass).newInstance());
		Class.forName(driverClass);
		
		//3.通过DriverManager的getConnection()方法获取数据库
		Connection connection=DriverManager.getConnection(jdbcUrl,user,password);
		System.out.println(connection);
	}
	
	public Connection getConnection2() throws Exception{
		//1.准备连接数据库的四个属性
		//	1.1创建Properties对象
		Properties properties=new Properties();
		
		//	1.2获取jdbc.properties对应的输入流
		InputStream in=this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
		
		//	1.3加载对应的输入流
		properties.load(in);
		
		//	1.4具体决定user,password等4个字符串
		String user=properties.getProperty("user");
		String password=properties.getProperty("password");
		String jdbcUrl=properties.getProperty("jdbcUrl");
		String driver=properties.getProperty("driver");
		
		//2.加载数据库驱动(对应的Driver实现类中有注册驱动的静态代码块)，使用DriverManager可注册多个驱动
		Class.forName(driver);
		
		//3.通过DriverManager的getConnection()方法获取数据库
		return DriverManager.getConnection(jdbcUrl,user,password);
	}
	
	@Test
	public void testGetConnection2() throws Exception{
		System.out.println(getConnection2());
	}
	
	/*
	 * 向指定的数据表中插入一条记录
	 * 
	 * 1.Statement:用于SQL语句的对象
	 * 	1.1  通过Connection的createStatement()方法来获取
	 * 	1.2  通过executeUpdate(sql)可以执行SQL语句
	 * 	1.3  传入的SQL可以是INSERT,UPDATE或DELETE,但不能是SELECT
	 * 
	 * 2.Connection、Statement都是应用程序和数据库服务器的连接资源，使用后一定要关闭
	 * 	需要在finally中关闭Connection和Statement对象。
	 * 
	 * 3.关闭的顺序是：先关闭后获取的，即关闭Statemrnt后关闭Connection
	 */
	@Test
	public void testStatement() throws Exception{
		//1.获取数据库连接
		Connection conn=null;
		Statement statement=null;
		try {
			conn=getConnection2();
			
			//2.准备插入的sql语句
//			String sql="INSERT INTO customers (name,email,birth) VALUES ('ABCD','ABCD@qq.com','1996-11-12')";
//			String sql="DELETE FROM customers where id=1";
			String sql="UPDATE customers SET name='tom'"+"where id=2";
			//3.执行插入
			//3.1  获取操作语句的Statement对象:调用Connection的createStatement()方法来获取
			statement=conn.createStatement();
			
			//3.2 调用Statement对象的ecxecuteUpdate(sql)执行SQL语句进行插入
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				//4.关闭Statement对象
				if(statement!=null){
					statement.close();
				}
			} catch (Exception e2) {
			}finally{
				//5.关闭连接
				if(conn!=null){
					conn.close();
				}
			}
		}
	}
	
	/*
	 * 通用的更新方法：包括INSERT UPDATE DELETE
	 * 版本1
	 */
	public void update(String sql){
		Connection conn=null;
		Statement statement=null;
		try {
			conn=JDBCTools.getConnection();
			statement=conn.createStatement();
			statement.executeUpdate(sql);
		} catch (Exception e) {
		}finally{
			JDBCTools.release(statement, conn);
		}
	}

	/**
	 * ResultSet:结果集，封装了使用JDBC进行查询的结果
	 * 1.调用Statement对象的executeQuery(sql)可以得到结果集
	 * 2.ResultSet返回的实际上就是一张数据表，有一个指针指向数据表的第一行的前面，
	 * 	可以调用next()方法检测下一行是否有效，若该方法有效返回true,且指针下移
	 * 	相当于Iterator对象的hasnext()和next()方法的结合体
	 * 3.当指针定位到一行时，可以通过调用getXxx(index)或调用getXxx(columnName)
	 * 	获取每一列的值，例如:getInt(1),getString("name")
	 * 4.Result当然也需要进行关闭
	 */
	@Test
	public void testResultSet(){
		//获取id=2的customers数据表的记录，并打印
		
		Connection conn=null;
		Statement statement=null;
		ResultSet rs=null;
		try {
			//1.获取Connection
			conn=JDBCTools.getConnection();
					
			//2.获取Statement
			statement=conn.createStatement();
			
			//3.准备SQL
			String sql="SELECT id,name,email,birth FROM customers";
			
			//4.执行查询，得到ResultSet
			rs=statement.executeQuery(sql);
			
			//5.处理ResultSet
			while(rs.next()){
				int id=rs.getInt(1);
				String name=rs.getString("name");
				String email=rs.getString(3);
				Date birth=rs.getDate(4);
				
				System.out.println(id);
				System.out.println(name);
				System.out.println(email);
				System.out.println(birth);
			}
			
		} catch (Exception e) {
			
		}finally{
			//6.关闭数据库资源
			JDBCTools.release(rs, statement, conn);
		}
	}
	
	
	//意外小测试
	@Test
	public void testStudent(){
		Student student=new Student();
		student.setId(1);
		student.setName("aa");
		Student student1=new GetS().getStudent(student);
		System.out.println(student1);
	}

}
