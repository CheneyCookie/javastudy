package com.cheney.annotation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestAnnotation {
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@Test
	public void helloAnnotation(){
		List list=new ArrayList();
	}
	
	@HelloAnnotation(age=12,major="java")
	class A{
		
		@HelloAnnotation(age=13,major="C",school="c")
		void test(){}
		
		void test2(@Deprecated String name){}
		
		@Deprecated
		void test3(){}
		
		
	}
}
