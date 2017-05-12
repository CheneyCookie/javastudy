package com.cheney.jdbc3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.junit.Test;

public class JDBCTest {
	
	/*
	 * 取得数据库自动生成的主键
	 */
	@Test
	public void testGetKeyValue(){
		
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try {
			String sql="INSERT INTO customers(name,email,birth) VALUES(?,?,?)";
			connection=JDBCTools.getConnection();
//			preparedStatement=connection.prepareStatement(sql);
			
			//使用重载的prepareStatement(sql,flag)来生成PreparedStatement对象
			preparedStatement=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, "abc");
			preparedStatement.setString(2, "abc@qq.com");
			preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));
			
			preparedStatement.executeUpdate();
			
			//通过getGeneratedKeys()获取包含了新生成的主键的Result对象
			//在ResultSet中只有一列GENERATED_KEY,用于存放新生成的主键值
			ResultSet rs=preparedStatement.getGeneratedKeys();
			if(rs.next()){
				System.out.println(rs.getObject(1));
			}
			
			ResultSetMetaData rsmd=rs.getMetaData();
			for(int i=0;i<rsmd.getColumnCount();i++){
				System.out.println(rsmd.getColumnName(i+1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
	}
	
	/*
	 * 插入BLOB类型的数据必须使用PreparedStatement:因为BLOB类型的数据是无法使用字符串拼写的
	 * 
	 * 调用setBlob(int index,InputStream in)
	 */
	@Test
	public void testInsertBlob(){
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		try {
			String sql="INSERT INTO user(name,password,picture) VALUES(?,?,?)";
			connection=JDBCTools.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			
			preparedStatement.setString(1, "abc");
			preparedStatement.setString(2, "123456");
			
			InputStream in=new FileInputStream("Hydrangeas.jpg");
			preparedStatement.setBlob(3, in);
			
			preparedStatement.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(null, preparedStatement, connection);
		}
	}
	
	/*
	 * 读取Blob数据：
	 * 	1.使用getBlob方法读取到Blob对象
	 * 	2.调用Blob的getBinaryStream()方法得到输入流。再使用IO操作即可
	 */
	@Test
	public void testReadBlob(){
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		try {
			String sql="SELECT * FROM user WHERE id=1";
			connection=JDBCTools.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next()){
				int id=resultSet.getInt(1);
				String name=resultSet.getString(2);
				String password=resultSet.getString(3);
				System.out.println(id+","+name+","+password);
				
				Blob picture=resultSet.getBlob(4);
				
				InputStream in=picture.getBinaryStream();
				System.out.println(in.available());
				OutputStream out=new FileOutputStream("flower.jpg");
				
				byte[] buffer=new byte[1024];
				int len=0;
				while((len=in.read(buffer))!=-1){
					out.write(buffer,0,len);
				}
				
				out.close();
				in.close();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.releaseDB(resultSet, preparedStatement, connection);
		}
	}
}
