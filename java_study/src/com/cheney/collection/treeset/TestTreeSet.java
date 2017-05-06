package com.cheney.collection.treeset;


import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TestTreeSet {

	
	@Test
	public void testTreeSetWithComparable() {
		TreeSet set=new TreeSet();
		set.add(new Person("小明",12));
		set.add(new Person("小红",13));
		set.add(new Person("marry",11));
		set.add(new Person("jerry",15));
		set.add(new Person("cookie",10));
		
		Iterator it=set.iterator();
		
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
	
	@Test
	public void testTreeSetWithComparator(){
		Comparator comparator=new Comparator() {
			//创建Comparator接口的实现类
			//实现compare(Object o1, Object o2)方法比较两个对象的大小
			@Override
			public int compare(Object o1, Object o2) {
				if(o1 instanceof Person1&& o2 instanceof Person1){
					Person1 p1=(Person1) o1;
					Person1 p2=(Person1) o2;
					return p1.getAge()-p2.getAge();
				}
				throw new ClassCastException("不能转为Person1类型");
			}
			
		};
		
		//创建TreeSet对象，传入Comparator接口的实现类对象
		TreeSet set=new TreeSet(comparator);
		set.add(new Person1("小明",12));
		set.add(new Person1("小红",13));
		set.add(new Person1("marry",11));
		set.add(new Person1("jerry",15));
		set.add(new Person1("cookie",10));
		
		Iterator it=set.iterator();
		
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
	
	@Test
	public void testTreeSet(){
		
		Comparator comparator=new Comparator() {
			//创建Comparator接口的实现类
			//实现compare(Object o1, Object o2)方法比较两个对象的大小
			@Override
			public int compare(Object o1, Object o2) {
				if(o1 instanceof Person1&& o2 instanceof Person1){
					Person1 p1=(Person1) o1;
					Person1 p2=(Person1) o2;
					return p1.getAge()-p2.getAge();
				}
				throw new ClassCastException("不能转为Person1类型");
			}
			
		};
		
		//创建TreeSet对象，传入Comparator接口的实现类对象
		TreeSet set=new TreeSet(comparator);
		set.add(new Person1("小明",12));
		set.add(new Person1("小红",13));
		set.add(new Person1("marry",11));
		set.add(new Person1("jerry",15));
		set.add(new Person1("cookie",10));
		
		Iterator it=set.iterator();
		
		while(it.hasNext()){
			System.out.println(it.next());
		}
		
		
//		Comparator comparator
//		Object first()
		Object object=set.first();
		System.out.println(object);
//		Object last()
//		Object lower(Object e)
//		Object higer(Object e)
//		SortedSet subSet(fromElement, toElement)
//		SortedSet headSet(toElement)
//		SortedSet tailSet(fromElement)
		//从所选对象new Person1("小红",13)开始以后的元素
		Set subSet=set.tailSet(new Person1("小红",13));
		System.out.println();
		System.out.println(subSet);
		
	}

}
