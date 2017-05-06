package com.cheney.test;
public class StaticTest{

	String name;
	static String clazz;
	{
		name="cheney";
		System.out.println("----代码块----");
	}

	static{
		clazz="���һ��";
		System.out.println("----静态代码块----");
	}


	public StaticTest(){
		System.out.println("----构造方法----");
	}


	public void p1(){
		clazz="软会141";
		name="cookie";
		System.out.println("p1:clazz : "+clazz);
		System.out.println("p1:name : "+name);
	}

	public void p2(){
		System.out.println("p2:clazz : "+clazz);
		System.out.println("p2:name : "+name);
	}
}