package com.cheney.jdbc4;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ReflectionUtil {

	public static Method getDeclaredMethod(Class clazz,String methodName,Class... parameterTypes){
		for(;clazz!=Object.class;clazz=clazz.getSuperclass()){
			try {
				Method method=clazz.getDeclaredMethod(methodName, parameterTypes);
				return method;
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			}
		}
		return null;
	}
	
	public static Object invokeMethod(Object obj,String methodName,Object... args){
		Class[] parameterTypes=new Class[args.length];
		for(int i=0;i<args.length;i++){
			parameterTypes[i]=args[i].getClass();
		}
		try {
			Method method=getDeclaredMethod(obj.getClass(), methodName, parameterTypes);
			method.setAccessible(true);
			return method.invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object invokeMethod(String className,String methodName,Object... args){
		Object obj=null;
		try {
			obj=Class.forName(className).newInstance();
			return invokeMethod(obj, methodName, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Field getField(Class clazz,String fieldName){
		Field field=null;
		for(Class clazz2=clazz;clazz2!=Object.class;clazz2=clazz2.getSuperclass()){
			try {
				field=clazz2.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return field;
	}
	
	public static Object getFieldValue(Object obj,Field field){
		field.setAccessible(true);
		Object obj2=null;
		try {
			obj2=field.get(obj);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj2;
	}
	
	public static void setFieldValue(Object obj,Field field,Object val){
		field.setAccessible(true);
		try {
			field.set(obj, val);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static Class getGenericSuperClassType(Class clazz,int index){
		Type type=clazz.getGenericSuperclass();
		if(!(type instanceof ParameterizedType)){
			return null;
		}
		ParameterizedType parameterizedType=(ParameterizedType) type;
		System.out.println(parameterizedType+"111");
		Type[] args=parameterizedType.getActualTypeArguments();
		if(index<0||index>=args.length){
			return null;
		}
		if(!(args[index] instanceof Class)){
			return null;
		}
		return (Class) args[index];
	}
	
	public static Class getGenericSuperClassType(Class clazz){
		return getGenericSuperClassType(clazz, 0);
	}
}
