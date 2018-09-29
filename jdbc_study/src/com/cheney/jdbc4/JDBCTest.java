package com.cheney.jdbc4;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCTest {
	/*
	 * mysql插入速度差别不大，未装Oracle
	 * 向customer数据表中插入十万条记录
	 * 测试如何插入,用时最短
	 * 1.还用Statement
	 * 10815,9920
	 */
	@Test
	public void testBatchWithStatement(){
		Connection connection=null;
		Statement statement=null;
		String sql=null;
		
		try {
			connection=JDBCTools.getConnection();
			JDBCTools.beginTx(connection);
			statement=connection.createStatement();
			
			long begin=System.currentTimeMillis();
			for(int i=0;i<100000;i++){
				sql="INSERT INTO customers(name,email) VALUES('"+i+"','abc@qq.com')";
				statement.executeUpdate(sql);
			}
			long end=System.currentTimeMillis();
			
			System.out.println("Time："+(end-begin));
			
			JDBCTools.commit(connection);
			
		} catch (Exception e) {
			e.printStackTrace();
			JDBCTools.rollback(connection);
		}finally{
			JDBCTools.releaseDB(null, statement, connection);
		}
	}
	
	/*
	 * 11039,9726
	 */
	@Test
	public void testBatchWithPreparedStatement(){
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String sql=null;
		
		try {
			connection=JDBCTools.getConnection();
			JDBCTools.beginTx(connection);
			sql="INSERT INTO customers(name,email) VALUES(?,?)";
			preparedStatement=connection.prepareStatement(sql);
			
			long begin=System.currentTimeMillis();
			for(int i=0;i<100000;i++){
				preparedStatement.setString(1, "name_"+i);
				preparedStatement.setString(2, "123@qq.com");
				preparedStatement.executeUpdate();
				
				JDBCTools.commit(connection);
			}
			long end=System.currentTimeMillis();
			
			System.out.println("Time："+(end-begin));
			
			JDBCTools.commit(connection);
			
		} catch (Exception e) {
			e.printStackTrace();
			JDBCTools.rollback(connection);
		}finally{
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
	}
	
	/*
	 * 10658
	 */
	@Test
	public void testBatch(){
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		String sql=null;
		
		try {
			connection=JDBCTools.getConnection();
			JDBCTools.beginTx(connection);
			sql="INSERT INTO customers(name,email) VALUES(?,?)";
			preparedStatement=connection.prepareStatement(sql);
			
			long begin=System.currentTimeMillis();
			for(int i=0;i<100000;i++){
				preparedStatement.setString(1, "name_"+i);
				preparedStatement.setString(2, "123@qq.com");
				
				//积攒SQL
				preparedStatement.addBatch();
				//当积攒到一定程度就统一执行 一次，并清空先前积攒的SQL
				if((i+1)%300==0){
					preparedStatement.executeBatch();
					preparedStatement.clearBatch();
				}
			}
			//若总条数不是批量数值的整数倍，则还需要再额外执行一次
			if(100000%300!=0){
				preparedStatement.executeBatch();
				preparedStatement.clearBatch();
			}
			
			long end=System.currentTimeMillis();
			
			System.out.println("Time："+(end-begin));
			
			JDBCTools.commit(connection);
			
		} catch (Exception e) {
			e.printStackTrace();
			JDBCTools.rollback(connection);
		}finally{
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
	}
	
	/*
	 * 使用DBCP数据库连接池
	 * 	1.加入jar包(2个).依赖于commons-pool-1.6.jar
	 * 	2.创建数据库连接池
	 * 	3.为数据源实例指定必须的属性
	 */
	@SuppressWarnings("resource")
	@Test
	public void testDBCP() throws SQLException{
		//1.创建DBCP数据源实例
		final BasicDataSource dataSource=new BasicDataSource();
		
		//2.为数据源实例指定必须的属性
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setUrl("jdbc:mysql:///test");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		//3.指定数据源的一些可选属性
		//	1.指定数据库连接池中初始化连接的个数
		dataSource.setInitialSize(5);
		//	2.指定最大的连接数:同一时刻可以向数据库申请的连接数
		dataSource.setMaxActive(5);
		//	3.指定最小连接数:在数据库连接池中保存的最少空闲连接的数量
		dataSource.setMinIdle(2);
		//	4.等待数据库连接池分配连接的最长时间，单位为毫秒，超过该 时间将抛出异常
		dataSource.setMaxWait(1000*5);
		
		//4.从数据源中获取数据库连接
		Connection connection=dataSource.getConnection();
		System.out.println(connection.getClass());
		System.out.println(connection);
		
		connection=dataSource.getConnection();
		System.out.println(connection.getClass());
		
		connection=dataSource.getConnection();
		System.out.println(connection.getClass());
		
		connection=dataSource.getConnection();
		System.out.println(connection.getClass());
		
		connection=dataSource.getConnection();
		System.out.println(connection.getClass()+"5");
		
		Connection connection2=dataSource.getConnection();
		System.out.println(connection2.getClass());
		
		new Thread(){
			public void run() {
				Connection conn;
				try {
					conn=dataSource.getConnection();
					System.out.println(conn.getClass());
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		connection2.close();
	}
	
	/*
	 * 1.加载dbcp的properties配置文件：配置文件中的键值对需要来自BasicDataSource的属性
	 * 2.调用BasicDataSourceFactory的createDataSource(),创建DataSource实例
	 * 3.从DataSource实例中获取数据库连接
	 */
	@Test
	public void testDBCPWithDataSourceFactory() throws Exception{
		Properties properties=new Properties();
		InputStream in=JDBCTest.class.getClassLoader().getResourceAsStream("dbcp.properties");
		properties.load(in);
		
		DataSource dataSource=BasicDataSourceFactory.createDataSource(properties);
		System.out.println(dataSource.getConnection());
		
		BasicDataSource basicDataSource=(BasicDataSource) dataSource;
		System.out.println(basicDataSource.getMaxWait());
		
	}
	
	@Test
	public void testC3P0() throws Exception{
		ComboPooledDataSource cpds=new ComboPooledDataSource();
		cpds.setDriverClass("com.mysql.jdbc.Driver");
		cpds.setJdbcUrl("jdbc:mysql:///test");
		cpds.setUser("root");
		cpds.setPassword("root");
		
		System.out.println(cpds.getConnection());
	}
	
	/*
	 * 1.创建c3p0-config.xml 参考帮助文档中Appendix B:Configuation Files的内容
	 * 2.创建ComboPooledDataSource实例:
	 * DataSource dataSource=new ComboPooledDataSource("helloc3p0");
	 * 3.从DataSource实例中获取数据库连接
	 */
	@Test
	public void testC3p0WithConfigFile(){
		DataSource dataSource=new ComboPooledDataSource("helloc3p0");
		try {
			System.out.println(dataSource.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ComboPooledDataSource comboPooledDataSource=(ComboPooledDataSource) dataSource;
		System.out.println(comboPooledDataSource.getMaxStatements());
	}
	
	@Test
	public void testJdbcTools() throws ClassNotFoundException, IOException, SQLException{
		Connection connection=JDBCTools.getConnection();
		System.out.println(connection);
	}
	
	/*
	 * 如何使用JDBC调用存储在数据库中的函数或存储过程
	 */
	@Test
	public void testCallableStatement(){
		Connection connection=null;
		CallableStatement callableStatement=null;
		
		try {
			connection=JDBCTools.getConnection();
			String sql="{?= call sum_salary(?,?,...)}";
			callableStatement=connection.prepareCall(sql);
			
			callableStatement.registerOutParameter(1, Types.NUMERIC);
			callableStatement.registerOutParameter(3, Types.NUMERIC);
			
			callableStatement.setInt(2, 80);
			
			callableStatement.execute();
			
			double sumSalary=callableStatement.getDouble(1);
			long empCount=callableStatement.getLong(3);
			System.out.println(sumSalary+","+empCount);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(null, callableStatement, connection);
		}
	}
}
