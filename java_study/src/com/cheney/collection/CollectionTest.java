package com.cheney.collection;

import java.util.*;

import com.cheney.collection.Person;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CollectionTest
{

	//add:添加一个元素到集合中
	
	public void test1(){
		System.out.println("----测试add----");
		Collection collection = new ArrayList();
		System.out.println(collection.size());

		collection.add("ABC");
		collection.add(new Person("qwe",12));
		collection.add(new Person("asd",13));
		collection.add(new Person("zxc",14));
		System.out.println(collection.size());
	}

	public void test2(){
		System.out.println("----测试addAll----");
		
		Collection collection2=new ArrayList();
		collection2.add("EFG");
		Collection collection = new ArrayList();

		collection.add("ABC");
		collection.add(new Person("qwe",12));
		collection.add(new Person("asd",13));
		collection.add(new Person("zxc",14));
		collection2.addAll(collection);
		System.out.println(collection2.size());
	}

	public void testIterator(){
		System.out.println("----测试Iterator----");
		Collection collection = new ArrayList();

		collection.add("ABC");
		collection.add(new Person("qwe",12));
		collection.add(new Person("asd",13));
		collection.add(new Person("zxc",14));
		for (Object obj:collection)
		{
			System.out.println(obj);
		}

		System.out.println();

		Iterator it=collection.iterator();
		while(it.hasNext()){
			Object obj=it.next();
			System.out.println(obj);
		}
	}

	
	public void removeOrClear(){
		System.out.println("----测试remove和clear----");
		Collection collection = new ArrayList();

		collection.add("ABC");
		Person p=new Person("qwe",12);
		collection.add(p);
		collection.add(new Person("asd",13));
		collection.add(new Person("zxc",14));
		System.out.println(collection.size());
		collection.remove(p);
		System.out.println(collection.size());
		collection.clear();
		System.out.println(collection.size());
	}

	public void removeAndRetainAll(){
		System.out.println("----测试removeAll和retainAll----");
		Collection collection = new ArrayList();

		collection.add("ABC");
		Person p=new Person("qwe",12);
		collection.add(p);
		collection.add(new Person("asd",13));
		collection.add(new Person("zxc",14));
		System.out.println(collection.size());

		Collection collection2 = new ArrayList();
		collection2.add(p);
		//collection2.add(new Person("rty",15));
		collection2.add(new Person("asd",13));

		collection.removeAll(collection2);
		System.out.println(collection.size());

		Collection collection3 = new ArrayList();
		collection3.add("ABC");

		collection.retainAll(collection3);
		System.out.println(collection.size());
	}
}