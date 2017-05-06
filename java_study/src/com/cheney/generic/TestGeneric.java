package com.cheney.generic;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class TestGeneric {

	
	/*
	 * 不使用泛型时
	 * 1.集合中的元素并不安全，可以向集合中放入任何引用类型的对象
	 * 2.从集合中取出的对象都是Object类型，在具体操作时需要进行类型的强制转换，
	 * 那么在强制类型转换时，容易发生ClassCastException.
	 * */
	@Test
	public void helloGeneric() {
		List<Person> persons=new ArrayList<Person>();
		persons.add(new Person("AA",12));
		persons.add(new Person("BB",14));
		persons.add(new Person("CC",13));
		persons.add(new Person("DD",15));
		
		persons.add(new Student());
		
		Person person=persons.get(2);
		System.out.println(person);
	}
	
	
	@Test
	public void testCollectionGeneric(){
		Set<Person> persons=new TreeSet<>(new Comparator<Person>() {

			@Override
			public int compare(Person p1, Person p2) {
				
//				return p1.getAge()-p2.getAge();
				return -p1.getAge()+p2.getAge();
			}
			
		});
		persons.add(new Person("AA",12));
		persons.add(new Person("BB",14));
		persons.add(new Person("CC",13));
		persons.add(new Person("DD",15));
		
		Iterator<Person> it=persons.iterator();
		while(it.hasNext()){
			Person person=it.next();
			System.out.println(person.getAge());
		}
	}
	
	@Test
	public void testGenericAndExtends(){
		Object[] objs=new Object[10];
		Person[] persons=new Person[10];
		
		//Object是Person的父类，则Object的数组也是Person数组的父类
		objs=persons;
		
		List<Object> objlList=new ArrayList<Object>();
		List<Person> personlList=new ArrayList<Person>();
		
		
		//Object是Person的父类，但List<Object>却不是List<Person>的父类
		//用反证法，若可以，则意味着可以向objList中放入任何类型的对象，
		//而从personList中获取的却是person类型的对象，这不可能。
//		objLisr=personlList;
		
		List<Person> persons2=new ArrayList<Person>();
		print(persons2);
		
		List<Student> students=new ArrayList<Student>();
		print(students);
		
	}

	public void print(List<? extends Person> list){
		
	}
}
