package com.cheney.innerclass;

public class OuterClass
{
	private String name1="hello";
	private static String name2="你好";
	private int i=1;

	public void test(){
		System.out.println(name1);
		System.out.println(name2);
	}

	class InnerClass
	{
		private String name3="cheney";
		private int i=2;

		public void test(){
			int i=3;

			System.out.println(name1);
			System.out.println(name2);
			System.out.println(name3);

			System.out.println(i);
			System.out.println(this.i);
			System.out.println(OuterClass.this.i);
		}
	}
	
	static class StaticInnerClass
	{
		private String name4="cl";

		public void test(){
			System.out.println(name2);
			System.out.println(name4);
		}
		
	}
	 
}