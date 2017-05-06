package com.cheney.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//使用@interface来声明注解
@Target(value={ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HelloAnnotation {
	
	//使用接口中声明方法的方式来声明注解的属性
	String major();
	int age();
	
	String school() default "cheney";
}
