package com.json;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class JsonTest {
	
	private static Grade grade;
	private static Student student;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("----测试方法执行之前执行----");
		student=new Student();
		grade=new Grade();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("----测试方法之后执行----");
		student=null;
		grade=null;
	}

	//测试普通java对象转成json格式
	@Test
	public void testObject() {
		grade.setId(1);
		grade.setName("java");
		System.out.println(JSONSerializer.toJSON(grade));
	}

	//测试数组转成json格式
	@Test
	public void testArray() {
		grade.setId(1);
		grade.setName("java");
		Student student=new Student();
		student.setName("小王");
		student.setDate(new Date());
		Student student2=new Student();
		student2.setName("小李");
		List<Student> stuList=new ArrayList<Student>();
		stuList.add(student);
		stuList.add(student2);
		grade.setStulist(stuList);
		System.out.println(JSONSerializer.toJSON(grade));
	}
		
	//测试static时不能转化为json属性的
	//可以去掉get,set方法的static,此方法并不是很好
	@Test
	public void testStatic(){
		student.setDate(new Date());
		student.setName("admin");
		student.setAge(18);
		System.out.println(JSONSerializer.toJSON(student));
		
		//如果返回的是static，或者返回的类型不确定，那么可以采用map或者自己构建json格式
		JSONObject object=new JSONObject();
		object.put("age", student.getAge());
		object.put("date", student.getDate());
		object.put("name", student.getName());
		System.out.println(object);
	}
	
	//解决自关联问题
	@Test
	public void testSelf(){
		student.setDate(new Date());
		student.setName("admin");
		student.setStudent(new Student());
		//通过jsonConfig来过滤相应的参数
		JsonConfig config=new JsonConfig();
		//设置需要排除哪些字段
		config.setExcludes(new String[]{"date"});
		//设置如果有些字段是自关联则过滤STRICT:默认值，是否自关联都要转化
		//LENIENT：如果有自关联对象，则值设置为null;
		//NOPROP:如果有自关联，则忽略属性
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		System.out.println(JSONObject.fromObject(student,config));
	}
	
	//设置日期的处理方式
	@Test
	public void testDate(){
		student.setDate(new Date());
		student.setName("admin");
		JsonConfig config=new JsonConfig();
		//指定某个json类型的处理方式
		DateJsonValueProcessor dateValue=new DateJsonValueProcessor();
		config.registerJsonValueProcessor(Date.class, dateValue);
		System.out.println(JSONObject.fromObject(student,config));
	}
	
	@Test
	public void testJsonArray(){
		JSONObject obj=new JSONObject();
		obj.put("id", 123);
		obj.put("name", "admin");
		JSONObject obj2=new JSONObject();
		obj2.put("id", 234);
		obj2.put("name", "xyz");
		JSONArray array=new JSONArray();
		array.add(obj);
		array.add(obj2);
		//把array的对象存储到obj对象中
		JSONObject temp=new JSONObject();
		temp.put("array", array);//{array:[{"id":123,"name":"admin"},{"id":234,"name":"xyz"}]}
		System.out.println(JSONObject.fromObject(temp));
	}
}
