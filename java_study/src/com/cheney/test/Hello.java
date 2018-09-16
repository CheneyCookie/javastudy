package com.cheney.test;
public class Hello
{
	
	public static void main(String[] args){
		System.out.println("Hello!");
		
		//boolean异或，相同为假，不同为真
		Long tail=2000L;
		Long distance=1999L;
		Long story=1000L;
		if((tail>distance)^((story*2)==tail))
			System.out.println("1");
		if((distance+1!=tail)^((story*2)==distance))
			System.out.println("2");
		
		//try{return;}
		//finally{System.out.println("11111111111111111");}
		
		
		try{
			test();
		}catch(RuntimeException ex){
			System.out.println("Runtime");
		}
		System.out.println("end");
	}
	
	public void a(){
		System.out.println("a");
		return ;
		
	}
	
	public static void test(){
		try{
			System.out.println("test");
			throw new RuntimeException();
		}catch(Exception ex){
			System.out.println("Exception");
		}
	}
}
