package com.cheney.jdbc2;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

public class BeanUtilsTest {


	@Test
	public void testSetProperty() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Object object=new Student();
		System.out.println(object);
		
		BeanUtils.setProperty(object, "id", 123);
		System.out.println(object);
		
		Object val=BeanUtils.getProperty(object, "id");
		System.out.println(val);
	}

}
