package com.cheney.test;

public class ArugmentTest {
	static class IntA{
		private int a;

		public IntA(int a) {
			this.a = a;
		}

		public int getA() {
			return a;
		}

		public void setA(int a) {
			this.a = a;
		}
	}
	
	
	static void change(int a){
		a=3;
	}
	
	static void change(IntA a){
		a.setA(3);
	}
	
	static void changeRef(IntA a){
		a=new IntA(9);
	}
	
	public static void main(String[] args) {
		int a=2;
		change(a);
		System.out.println(a);
		IntA ia=new IntA(5);
		change(ia);
		System.out.println(ia.getA());
		changeRef(ia);
		System.out.println(ia.getA());
	}
}
