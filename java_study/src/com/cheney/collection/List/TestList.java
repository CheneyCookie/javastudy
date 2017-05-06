package com.cheney.collection.List;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TestList {

	@Test
	public void testList() {
	
	
		List list=new ArrayList();
		list.add(new Person("AA",12));
		list.add(new Person("BB",14));
		list.add(new Person("CC",16));
		list.add(new Person("DD",13));
		list.add(new Person("EE",15));
		
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
		System.out.println();
		
		Iterator it=list.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
		//void add(int index,Object ele):把元素添加到指定位置,原来的元素被后移
//	boolean addALL(int index,Collection eles):把一组元素添加到指定位置
//	Object get(int index)获取指定索引的元素
//	int indexOf(Object obj):获取指定元素的索引值,若元素不存在，则返回-1
	int index=list.indexOf(new Person("CC",16));
	System.out.println(index);
		
//	int lastIndexOf(Object obj):List中可以存放重复的元素，获取重复的元素的最索引
	list.add(new Person("DD",13));
	System.out.print(list.lastIndexOf(new Person("DD",13)));
//	Object remove(int index):移除指定索引的元素
//	Object set(int index,Object ele):设置第index位置的元素为ele,原来的元素被替换
//	List subList(int fromIndex,int toIndex)
	}
	/*
	 * 可变参数
	 * 1.若在定义方法时，在最后一个形参的类型后增加三点(...,  ...位于变量类型和变量名之间，前后有无空格都可以)则表明该形参可以接受多个参数值
	 * 多个参数值被当成数组传入
	 * 2.可变形参只能处于形参列表的最后，所以一个方法最多只能有一个长度可变的形参
	 * 3.调用包含一个可变形参的方法时，可以为该形参传入多个参数或一个数组
	 * 4.能匹配定长的方法，那么优先匹配该方法，含有不定参数的重载方法被最后匹配
	 * 5.main方法的参数就是一个数组类型的，它可以改成可变参数类型
	 * 6.调用可变参数的方法时，编译器为该可变参数隐含创建一个数组，在方法中以数组的形式访问
	 * */
	
	public void test(String ... args){
		System.out.println(args.length);
		for(int i=0;i<args.length;i++){
			System.out.println(args[i]);
		}
	}
	
	//编译出错
//	public void test(String ... args,int i){
//		
//	}
	
	public void test(String a,String b){
		System.out.println("指定参数个数的方法");
	}
	
	
	@Test
	public void testAttaysAsList(){
		test("aa","bb","cc","dd","ee");
		test(new String[]{"MM","NN"});
		
		test("aa","bb");
		
		
		/*
		 * 1.Arrays.asList(Object...args)方法返回的时一个List对象
		 * 但不是ArrayList也不是Vector
		 * 2.Arrays.asList(Object...args)方法返回的List是一个只读List
		 * */
		List list=Arrays.asList("a","b","c");
		System.out.println(list);
		System.out.println(list.getClass());
	}

}
