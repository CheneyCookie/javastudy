package com.cheney.test;

public class ExtendsTest extends Test1 {

	public String name = "lisi";

	public ExtendsTest() {
		print();
	}

	public void print() {
		System.out.println(name);
	}
	
	public static void main(String[] args) {
		new ExtendsTest();
	}
}

class Test1 {
	public String name = "zhangsan";

	public Test1() {
		print();
	}

	public void print() {
		System.out.println(name);
	}
}