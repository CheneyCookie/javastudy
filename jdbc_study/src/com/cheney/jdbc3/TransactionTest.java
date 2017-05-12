package com.cheney.jdbc3;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

/*
 * mysql命令语句
 * 	查看当前隔离级别:SELECT @@tx_isolation;
 * 	设置当前mysql连接的隔离级别:set transaction isolation level read committed(读已提交);
 * 	设置数据库系统的全局隔离级别:set global transaction isolation level read committed;
 */
public class TransactionTest {


	/*
	 * Tom给Jerry汇款500元
	 * 
	 * 关于事务：
	 * 	1.如果多个操作，每个操作使用的是自己单独的连接，无法保证事务
	 * 	2.具体步骤：
	 * 		1.事务操作开始前，开始事务：取消Connection的默认提交行为connection.setAutoCommit(false);
	 * 		2.如果事务操作都成功则提交事务connection.commit();
	 * 		3.回滚事务：若出现异常，则在catch块中回滚事务
	 * 
	 */
	@Test
	public void testTransaction() {
		
		Connection connection=null;
		
		try {
			connection=JDBCTools.getConnection();
			//开始事务：取消默认提交
			connection.setAutoCommit(false);
			
			String sql="UPDATE user SET balance = balance - 500 WHERE id=2";
			update(connection,sql);
			
			int i=10/0;
			
			sql="UPDATE user SET balance = balance + 500 WHERE id=3";
			update(connection,sql);
			
			//提交事务
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			//回滚事务
			try {
				connection.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}finally{
			JDBCTools.releaseDB(null, null, connection);
		}
		
//		Dao dao=new Dao();
//		
//		String sql="UPDATE user SET balance = balance - 500 WHERE id=2";
//		dao.update(sql);
//		
//		int i=10/0;
//		
//		sql="UPDATE user SET balance = balance + 500 WHERE id=3";
//		dao.update(sql);
	}

	public void update(Connection connection,String sql,Object... args){
		PreparedStatement preparedStatement = null;
		try {
			// 1.得到结果集
			preparedStatement = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			preparedStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(null, preparedStatement, null);
		}
	}
	
	/*
	 * 测试事务的隔离级别
	 * 在JDBC程序中可以通过Connection的setTransactionIsolation来设置事务的隔离级别
	 */
	@Test
	public void testTransactionIsolationUpdate(){
		Connection connection=null;
		
		try {
			connection=JDBCTools.getConnection();
			connection.setAutoCommit(false);
			
			String sql="UPDATE user SET balance = balance - 500 WHERE id=2";
			update(connection,sql);
			
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	@Test
	public void testTransactionIsolationRead(){
		String sql="SELECT balance FROM user WHERE id=2";
		Integer balance=getForValue(sql);
		System.out.println(balance);
	}
	
	public <E> E getForValue(String sql,Object... args){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// 1.得到结果集
			connection = JDBCTools.getConnection();
//			connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			
			
			preparedStatement = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				return (E) resultSet.getObject(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
		
		return null;
	}
}
