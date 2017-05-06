package com.cheney.date;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/*
 * DateFormat把日期对象格式化为一个字符串，并且把字符串转为一个Date对象
 * DateFormat:是一个抽象类，
 * 	抽象类对象获取方法：
 * 		1.创建其子类对象
 * 		2.有的抽象类中提供了静态工厂方法来获取抽象类的实例
 * */
public class TestDate {

	@Test
	public void testDate() {
		Date date=new Date();
		System.out.println(date);
	}
	
	@Test
	public void testDateFormat() throws ParseException{
		DateFormat dateFormat=DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.SHORT);
		Date date=new Date();
		String dateStr=dateFormat.format(date);
		System.out.println(dateStr);
		
		dateStr="2017年3月15日 下午7:12";
		Date date2=dateFormat.parse(dateStr);
		System.out.println(date2);
	}

	@Test
	public void testSimpleDateFormate() throws ParseException{
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date=new Date();
		System.out.println(dateFormat.format(date));
	
		String dateStr="1996-11-16 12:12:12";
		Date date2=dateFormat.parse(dateStr);
		System.out.println(date2);
	}
}
