package com.cheney.collection.properties;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class TestProperties {

	
	/*
	 * Properties对应.properties属性文件
	 * .properties中存放的时键值对，键值都是String类型的
	 * */
	@Test
	public void testProperties() throws IOException {
		//读取jdbc.properties
		
		//1.创建Properties对象
		Properties properties=new Properties();
		//2.调用Properties的load()方法对应的输入流
		InputStream inStream=TestProperties.class
				.getClassLoader()
				.getResourceAsStream("jdbc.properties");
		properties.load(inStream);
		//3.调用getProperties(String key)方法获取属性值
		String user=properties.getProperty("user");
		int time=Integer.parseInt(properties.getProperty("time"));
		System.out.println(time);
		
		System.out.println(user);
	}

}
