package com.cheney.singleton;
import com.cheney.singleton.Singleton;


public class Test
{
	public static void main(String[] args){
		Singleton s1=Singleton.getSingleton();
		System.out.println("Test:"+s1);
	}

}