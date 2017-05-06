package com.cheney.singleton;

public class Singleton
{
	private String name;
	private int[] count;


	private Singleton(){
		count=new int[5];
	}

	private static Singleton singleton=new Singleton();

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name=name;
	}

	public int[] getCount(){
		return count;
	}

	public void setCount(int[] count){
		this.count=count;
	}

	public static Singleton getSingleton(){
		System.out.println("Singleton:"+singleton);
		return singleton;
	}
}