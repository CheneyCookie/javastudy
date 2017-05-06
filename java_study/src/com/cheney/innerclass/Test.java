package com.cheney.innerclass;

import com.cheney.innerclass.OuterClass;
import com.cheney.innerclass.OuterClass.InnerClass;
import com.cheney.innerclass.OuterClass.StaticInnerClass;

public class Test
{
	public static void main(String[] args){
		OuterClass oc=new OuterClass();
		
		oc.test();

		InnerClass ic=oc.new InnerClass();

		ic.test();

		StaticInnerClass sic=new OuterClass.StaticInnerClass();

		
		sic.test();
	}
}