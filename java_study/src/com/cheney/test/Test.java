package com.cheney.test;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		method();
//		TheSon theSon=new TheSon();
//		
//		System.out.println(theSon);
		double a=3.4/0;
		System.out.println(a);
		
		String x="java";
		String y="ja"+"va";
//		String y="ja"+new String("va");
		String b="ja";
		String c="va";
		String d=b+c;
		System.out.println(x==y);
		System.out.println(d==x);

		Date date=new Date();
		Format format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(format.format(date));
		
		System.out.println(3.0%0);
		
		StringBuilder builder=new StringBuilder();
		builder.insert(0, "1");
		builder.insert(0, "2");
		builder.insert(0, "3");
		System.out.println(builder);
		
//		String s=null;
//		System.out.println(s==null);
//		System.out.println(s.length());
		
//		int x1=012;
//		System.out.println(x1);
//		char c="a";
//		String sss='sass';
		
//		String str="1234567";
//		str=str.substring(1,3);
//		System.out.println(str);
		
//		float f=1*3.0;
//		double n=3.0/0;
//		int m=(int) n;
//		System.out.println(m);
		
//		int[] i=new int[3];
//		long[] j=new long[3];
//		i=j;
		
//		final String s=new String("ddd");
		
	}

	private static void method() {
		System.out.println("a=100,b=200");
//		Thread.currentThread().stop();
	}
	
//	 void a(){
//		this.b();
//		String n=this.c;
//	}
//	static String c;
//	static void b(){
//		
//	}
	
}

//class A{
//	public static void main(String[] args) {
//		System.out.println("aaa");
//	}
//}