package com.cheney.jdbc4;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import org.junit.Test;

public class CustomerDaoTest {

	CustomerDao customerDao=new CustomerDao();

	@Test
	public void testUpdate() {
		
	}

	@Test
	public void testGet() {
		Connection connection=null;
		try {
			connection=JDBCTools.getConnection();
			String sql="SELECT * FROM customers WHERE id=?";
			Customer customer=customerDao.get(connection, sql, 2);
			System.out.println(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}

	@Test
	public void testGetForList() {
		Connection connection=null;
		try {
			connection=JDBCTools.getConnection();
			String sql="SELECT * FROM customers";
			List<Customer> customers=customerDao.getForList(connection, sql);
			System.out.println(customers);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(null, null, connection);
		}
	}

	@Test
	public void testGetForValue() {
		
	}

	@Test
	public void testBatch() {
		
	}

}
