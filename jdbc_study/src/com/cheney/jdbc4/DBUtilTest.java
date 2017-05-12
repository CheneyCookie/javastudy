package com.cheney.jdbc4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

/*
 * 测试DBUtil工具类
 */
public class DBUtilTest {
	QueryRunner queryRunner = new QueryRunner();

	class MyResultSetHandler implements ResultSetHandler {

		@Override
		public Object handle(ResultSet resultSet) throws SQLException {
			// System.out.println("handle...");
			// return "cheney";
			List<Customer> customers = new ArrayList<Customer>();

			while (resultSet.next()) {
				Integer id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				Date birth = resultSet.getDate(4);
				Customer customer = new Customer(id, name, email, birth);
				customers.add(customer);
			}
			return customers;
		}

	}

	/*
	 * 测试QueryRunner类的update方法 该方法可用于Insert,Update和DELETE
	 */
	@Test
	public void testQueryRunnerUpdate() {
		// 1.创建QueryRunner的实现类

		// 2.使用其Update方法
		String sql = "DELETE FROM customers WHERE id IN(?,?)";
		Connection connection = null;

		try {
			connection = JDBCTools.getConnection();
			queryRunner.update(connection, sql, 12, 13);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, null, connection);
		}
	}

	/*
	 * QueryRunner的query方法的返回值取决于其ResultSetHandle参数的handle方法的返回值
	 */
	@Test
	public void testQuery() {
		Connection connection = null;

		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT * FROM customers";
			Object object = queryRunner.query(connection, sql,
					new MyResultSetHandler());
			System.out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, null, connection);
		}
	}

	/*
	 * BeanHandler:把结果集的第一条记录转为创建BeanHandler对象时传入的Class参数对应的对象
	 */
	@Test
	public void testBeanHandler() {
		Connection connection = null;

		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT * FROM customers WHERE id=?";

			Customer customer = queryRunner.query(connection, sql,
					new BeanHandler(Customer.class), 3);
			System.out.println(customer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, null, connection);
		}
	}

	/*
	 * BeanListHandler:把结果集转为一个List,该List不为null;但可能为空集合（Size方法返回0）
	 * 若SQL语句的确能够查询到记录，List中存放创建BeanListHandler传入的Class对象对应的对象
	 */
	@Test
	public void testBeanListHandler() {
		Connection connection = null;

		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT * FROM customers";

			List<Customer> customers = queryRunner.query(connection, sql,
					new BeanListHandler(Customer.class));
			System.out.println(customers);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	/*
	 * MapHandler:返回SQL对应的第一条记录对应的Map对象
	 * 键：SQL查询的列名（不是列的别名，）值：列的值
	 */
	@Test
	public void testMapHandler(){
		Connection connection = null;

		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT * FROM customers";

			Map<String, Object> result = queryRunner.query(connection, sql,
					new MapHandler());
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	/*
	 * MapListHandler:将结果集转为一个Map的List
	 * Map对应查询的一条记录：键：SQL查询的列名（不是列的别名，）值：列的值
	 * 而MapListHandler:返回多条记录对应的Map集合
	 */
	@Test
	public void testMapListHandler(){
		Connection connection = null;

		try {
			connection = JDBCTools.getConnection();
			String sql = "SELECT * FROM customers";

			List<Map<String, Object>> result = queryRunner.query(connection, sql,
					new MapListHandler());
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, null, connection);
		}
	}
	
	/*
	 * ScalarHandler:把结果集转为一个数值（可以是任意基本数据类型和字符串，Date等）返回
	 */
	@Test
	public void testScalarHandler(){
		Connection connection = null;

		try {
			connection = JDBCTools.getConnection();
//			String sql = "SELECT name FROM customers WHERE id=?";
			String sql = "SELECT count(id) FROM customers";

			Object result = queryRunner.query(connection, sql,
					new ScalarHandler());
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(null, null, connection);
		}
	}
}
