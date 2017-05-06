package com.cheney.collection.enumeration;


import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TestEnumeration {

	@Test
	public void test() {
//		String str="a,b,c,d,e";
//		Enumeration enumeration=new StringTokenizer(str);
		Hashtable hashtable=new Hashtable();
		hashtable.put("A", "1");
		hashtable.put("B", "2");
		hashtable.put("C", "3");
		hashtable.put("D", "4");
		hashtable.put("E", "5");
		
		Enumeration enumeration=hashtable.elements();
		
		while(enumeration.hasMoreElements()){
			Object obj=enumeration.nextElement();
			System.out.println(obj);
		}
	}

}
