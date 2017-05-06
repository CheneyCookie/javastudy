package com.cheney.jdbc2;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

public class JDBCTest {

	@Test
	public void testAddNewStudent(){
		Student student=getStudentFromConsole();
		addNewStudent(student);
	}
	
	/*
	 * 从控制台输入学生的信息
	 */
	private Student getStudentFromConsole() {
		Scanner scanner=new Scanner(System.in);
		Student student=new Student();
		
		System.out.print("id:");
		student.setId(scanner.nextInt());
		System.out.print("name:");
		student.setName(scanner.next());
		System.out.print("age:");
		student.setAge(scanner.nextInt());
		
		return student;
	}

	public void addNewStudent(Student student) {
		String sql="INSERT INTO student (id,name,age) VALUES("+student.getId()+",'"+student.getName()+"',"+student.getAge()+")";
		JDBCTools.update(sql);
	}
	
	@Test
	public void testGetStudent(){
		Student student=searchStudent();
		printStudent(student);
	}

	private void printStudent(Student student) {
		if(student!=null){
			System.out.println(student);
		}else{
			System.out.println("查无此人");
		}
	}

	/*
	 * 根据sql返回student对象
	 */
	private Student searchStudent() {
		Scanner scanner=new Scanner(System.in);
		System.out.print("id:");
		int id=scanner.nextInt();
		String sql="SELECT id,name,age FROM student where id="+id;
		
		Student student=null;
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		
		try {
			connection=JDBCTools.getConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			
			if(resultSet.next()){
				student=new Student(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(resultSet, statement, connection);
		}
		
		return student;
	}

	@Test
	public void testPreparedStatement(){
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		try {
			connection=JDBCTools.getConnection();
			String sql="INSERT INTO customers(name,email,birth) VALUES(?,?,?)";
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, "chen");;
			preparedStatement.setString(2, "chen@qq.com");
			//设置java.sql.Date
			preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
	}
	
	public void addNewStudent2(Student student){
		String sql="INSERT INTO student(id,name,age) VALUES(?,?,?)";
		JDBCTools.update(sql,student.getId(),student.getName(),student.getAge());
	}
	
	@Test
	public void testAddNewStudent2(){
		Student student=getStudentFromConsole();
		addNewStudent2(student);
	}

	/*
	 * sql注入
	 */
	@Test
	public void testSQLInjection(){
//		String username ="tom";
//		String password="123456";
		String username="a' OR password =";
		String password=" OR '1'='1";
		String sql="SELECT * FROM user WHERE username='"+username+"' AND password='"+password+"' ";
		
		System.out.println(sql);
		
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		
		try {
			connection=JDBCTools.getConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			
			if(resultSet.next()){
				System.out.println("登录成功！");
			}else{
				System.out.println("用户名或密码错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(resultSet, statement, connection);
		}
	}
	
	/*
	 * 使用PrepapedStatement将有效解决SQL注入问题
	 */
	@Test
	public void testSQLInjection2(){
		String username="a' OR password =";
		String password=" OR '1'='1";
		String sql="SELECT * FROM user WHERE username=? AND password=?";
		
		System.out.println(sql);
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		try {
			connection=JDBCTools.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next()){
				System.out.println("登录成功！");
			}else{
				System.out.println("用户名或密码错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
	}
	
	public Student getStudent(String sql,Object... args){
		Student student=null;
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		try {
			connection=JDBCTools.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next()){
				student=new Student();
				student.setId(resultSet.getInt(1));
				student.setName(resultSet.getString(2));
				student.setAge(resultSet.getInt(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
		
		return student;
	}
	
	public Customer getCustomer(String sql,Object... args){
		Customer customer=null;
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		try {
			connection=JDBCTools.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next()){
				customer=new Customer();
				customer.setId(resultSet.getInt(1));
				customer.setName(resultSet.getString(2));
				customer.setEmail(resultSet.getString(3));
				customer.setBirth(resultSet.getDate(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
		
		return customer;
	}
	
	public <T>T get(Class<T> clazz,String sql,Object... args){
		T entity=null;
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		try {
			//1.得到ResultSet对象
			connection=JDBCTools.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet=preparedStatement.executeQuery();
			
			//2.得到ResultSetMetaData对象
			ResultSetMetaData rsmd=resultSet.getMetaData();
			
			//3.创建一个Map<String,Object>对象,键：sql查询列的别名，值：列的值
			Map<String , Object> values=new HashMap<String, Object>();
			
			//4.处理结果集，填充3对应的Map对象
			if(resultSet.next()){
				for(int i=0;i<rsmd.getColumnCount();i++){
					String columnLabel=rsmd.getColumnLabel(i+1);
					Object columnValue=resultSet.getObject(i+1);
					
					values.put(columnLabel, columnValue);
				}
			}
			
			//5.若Map不为空集，利用反射创建clazz对应的对象
			if(values.size()>0){
				entity=clazz.newInstance();
				//6.遍历Map对象，利用发射Class对象为对应属性赋值
				for(Map.Entry<String , Object> entry:values.entrySet()){
					String fieldName=entry.getKey();
					Object fieldValue=entry.getValue();
					Field field=ReflectionUtil.getField(entity.getClass(), fieldName);
					ReflectionUtil.setFieldValue(entity, field, fieldValue);
				}
			}
			
			if(resultSet.next()){
				//利用反射创建对象
				entity=clazz.newInstance();
				//通过解析SQL语句来判断选择了哪些列，以及需要为entity对象的哪些属性赋值
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
		return entity;
	}
	
	@Test
	public void testGet(){
		String sql="SELECT id id,name,email,birth FROM customers WHERE id=?";
		Customer customer=get(Customer.class, sql, 4);
		System.out.println(customer);
		sql="SELECT id,name,age FROM student WHERE id=?";
		Student student=get(Student.class, sql, 1);
		System.out.println(student);
	}
	
	/*
	 * ResultSetMetaData：是描述resultSet的元数据对象，即从中可以获取到结果集中有多少列，列名是什么
	 * 	使用：1.的到ResultSetMetaData对象：调用ResultSet的getMetaData()方法
	 * 		2.ResultSetMetaData好用的方法：
	 * 			1.int getColumnCount():SQL语句中包含哪些列
	 * 			2.String getColumnLabel(int column):获取指定的列的别名，其中索引从1开始
	 */
	@Test
	public void testResultSetMetaData(){
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		try {
			String sql="SELECT id id,name,age FROM student WHERE id=?";
			connection=JDBCTools.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setObject(1,1);
			
			resultSet=preparedStatement.executeQuery();
			
			Map<String, Object> values=new HashMap<String, Object>();
			
			//1.得到ResultSetMetaData对象
			ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
			
			while(resultSet.next()){
				//2.打印每一列的列名
				for(int i=0;i<resultSetMetaData.getColumnCount();i++){
					String columnLabel=resultSetMetaData.getColumnLabel(i+1);
					Object columnValue=resultSet.getObject(columnLabel);
					
					values.put(columnLabel, columnValue);
				}
			}
			System.out.println(values);
			
			Class clazz=Student.class;
			Object object=clazz.newInstance();
			for(Map.Entry<String , Object> entry:values.entrySet()){
				String fieldName=entry.getKey();
				Object fieldValue=entry.getValue();
				System.out.println(fieldName+":"+fieldValue);
				Field field=ReflectionUtil.getField(object.getClass(), fieldName);
				ReflectionUtil.setFieldValue(object, field, fieldValue);
			}
			System.out.println(object);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
	}
}
