package com.cheney.jdbc4;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * 使用QueryRunner提供其具体实现
 * @author cheney
 *
 * @param <T>:子类需传入的泛型类型
 */
public class JdbcDaoImpl<T> implements Dao<T>{

	private QueryRunner queryRunner=null;
	private Class<T> type;
	
	public JdbcDaoImpl() {
		queryRunner=new QueryRunner();
		type=ReflectionUtil.getGenericSuperClassType(getClass());
		System.out.println(getClass());
		System.out.println(type);
	}
	
	@Override
	public void update(Connection connection, String sql, Object... args) throws SQLException {
		queryRunner.update(connection, sql, args);
	}

	@Override
	public T get(Connection connection, String sql, Object... args) throws SQLException {
		
		return queryRunner.query(connection, sql, new BeanHandler<>(type),args);
	}

	@Override
	public List<T> getForList(Connection connection, String sql, Object... args) throws SQLException {
		return queryRunner.query(connection, sql, new BeanListHandler<>(type),args);
		
	}

	@Override
	public Object getForValue(Connection connection, String sql, Object... args) throws SQLException {
		return queryRunner.query(connection, sql, new ScalarHandler<>(),args);
	}

	@Override
	public void batch(Connection connection, String sql, Object[]... args) {
		
	}

}
