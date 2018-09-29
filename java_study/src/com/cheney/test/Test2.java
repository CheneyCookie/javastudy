package com.cheney.test;

import java.util.Scanner;

import org.junit.Test;

public class Test2 {

	@Test
	public void test() {
		int a = 1;
		int b = 2;
		int c = 3;
		int res = (a++) * (++b) * (--c) + (a--) * (--b) * (c++);
		System.out.println(res);
	}

	@Test
	public void test1() {
		Object[][] o = new Object[3][3];
		System.out.println(o[1]);
	}

	@Test
	public void test3() {
		Scanner sc = new Scanner(System.in);
		int i = sc.nextInt();
		System.out.println(change(i));

	}
	@Test
	public void test4() {
		String a="hello";
		String b="hello";
		System.out.println(a==b);
	}

	public int change(int i) {
		System.out.println(i);
		StringBuffer sb = new StringBuffer(i+"");
		
		String str = sb.reverse().toString();
		System.out.println(str);
		int j = Integer.parseInt(str);
		return j + i;
	}
}
