package com.cheney.jdbc3;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class Dao {
	
	/*
	 * 增删改
	 */
	public void update(String sql,Object... args){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			// 1.得到结果集
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			preparedStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
	}

	/*
	 * 获取某一个值
	 */
	public <E> E getForValue(String sql,Object... args){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// 1.得到结果集
			connection = JDBCTools.getConnection();
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
	
	/*
	 * 获取一个对象
	 */
	public <T> T get(Class<T> clazz,String sql,Object... args){
		List<T> result=getForList(clazz, sql, args);
		
		if(result.size()>0){
			return result.get(0); 
		}
		return null;
	}
	
	/*
	 * 获取一组对象
	 */
	public <T> List<T> getForList(Class<T> clazz, String sql, Object... args) {
		List<T> list = new ArrayList<T>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// 1.得到结果集
			connection = JDBCTools.getConnection();
			preparedStatement = connection.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}

			resultSet = preparedStatement.executeQuery();

			// 2.处理结果集，得到Map对应的List,其中一个Map对象就是一条记录，
			// Map的key为resultSet中列的别名，Map的value为列的值
			List<Map<String, Object>> values = handleResultSetToMapList(resultSet);

			// 3.把Map的List转为clazz对应的List,其中Map的key即为对应的对象的propertyName,
			// 而Map的value即为clazz对应的对象的propertyValue
			list=transfterMapListToBeanList(clazz, values);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
		return list;
	}
	
	private <T> List<T> transfterMapListToBeanList(Class<T> clazz,
			List<Map<String, Object>> values) throws InstantiationException,
			IllegalAccessException, InvocationTargetException {

		List<T> result = new ArrayList<T>();

		T bean = null;
		if (values.size() > 0) {
			for (Map<String, Object> m : values) {
				bean = clazz.newInstance();

				for (Map.Entry<String, Object> entry : m.entrySet()) {
					String propertyName = entry.getKey();
					Object value = entry.getValue();

					BeanUtils.setProperty(bean, propertyName, value);
				}

				result.add(bean);
			}
		}
		return result;
	}

	/*
	 * 处理结果集，得到Map的一个List
	 */
	private List<Map<String, Object>> handleResultSetToMapList(
			ResultSet resultSet) throws SQLException {
		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();

		List<String> columnLabels = getColumnLabels(resultSet);
		Map<String, Object> map = null;

		while (resultSet.next()) {
			map = new HashMap<String, Object>();
			for (String columnLabel : columnLabels) {
				Object value = resultSet.getObject(columnLabel);
				map.put(columnLabel, value);
			}
			values.add(map);
		}
		return values;
	}

	/*
	 * 获取结果集的ColumnLabel对应的List
	 */
	private List<String> getColumnLabels(ResultSet rs) throws SQLException {
		List<String> labels = new ArrayList<String>();

		ResultSetMetaData rsmd = rs.getMetaData();
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			labels.add(rsmd.getColumnLabel(i + 1));
		}

		return labels;
	}
}
