package com.cheney.test;
public class StaticTest{
	static {
		b=4;
	}
	static int b=8;
	int a=9;
{
		
		a=6;
	}
	String name;
	static String clazz;
	{
		name="cheney";
		System.out.println("----代码块----");
	}

	static{
		clazz="���һ��";
//		b=4;
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
	public static void main(String[] args) {
		System.out.println(new StaticTest().a);
		System.out.println(b);
	}
	
}