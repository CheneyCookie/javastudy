package com.cheney.collection.collections;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.cheney.collection.Person;
import com.cheney.collection.map.Person1;

public class TestCollections {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testCollections() {
		//1.获取线程安全的集合对象
		/*
		 * ArrayList、HashSet、HashMap...都不是线程安全的
		 * 如何得到一个线程安全的对象?
		 * 
		 * 调用Collections的synchronizedXxx方法获取线程安全的对象
		 * */
		List list=new ArrayList();
		List list2=Collections.synchronizedList(list);
		
		//2.排序方法
		List list3=new ArrayList();
		list3.add(new Person("AA",12));
		list3.add(new Person("ZZ",8));
		list3.add(new Person("MM",14));
		list3.add(new Person("RR",16));
		list3.add(new Person("CC",13));
		
		for(Object obj:list3){
			System.out.println(obj);
		}
		System.out.println();
		
		Collections.sort(list3,new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				if(o1 instanceof Person &&o2 instanceof Person){
					Person p1=(Person) o1;
					Person p2=(Person) o2;
					return p1.getAge()-p2.getAge();
				}
				throw new ClassCastException("不能转为Person类型");
			}
		});
		for(Object obj:list3){
			System.out.println(obj);
		}
		//3.获取集合中最小的元素，要求集合中的元素都实现Comparable接口
		Set set=new HashSet();
		set.add(new Person1("AA",12));
		set.add(new Person1("ZZ",8));
		set.add(new Person1("MM",14));
		set.add(new Person1("RR",16));
		set.add(new Person1("CC",13));
		Object obj=Collections.min(set);
		System.out.println(obj);
	}

}
