package com.cheney.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class Dao<T> {
	
	private Class<T> clazz;
	
	public Dao(){
//		System.out.println("Dao's Constructor");
//		System.out.println(this);
//		System.out.println(this.getClass());
		
		//获取Dao子类的父类
//		Class clazz2=this.getClass().getSuperclass();
//		System.out.println(clazz2);
		
		//获取Dao带泛型参数的父类：Dao<Person>
		Type type=this.getClass().getGenericSuperclass();
		System.out.println(type);
		
		//获取具体的泛型参数
		if(type instanceof ParameterizedType){
			ParameterizedType parameterizedType=(ParameterizedType) type;
			
			Type[] args=parameterizedType.getActualTypeArguments();
			System.out.println(Arrays.asList(args));
			
			if(args!=null&&args.length>0){
				Type arg=args[0];
				System.out.println(arg);
				if(arg instanceof Class){
					clazz=(Class<T>) arg;
				}
			}
		}
	}
	
	void save(T entity){
		
	}
	
	T get(Integer id){
		System.out.println(clazz);
		return null;
	}
}
