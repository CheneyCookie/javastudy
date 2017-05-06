package com.cheney.collection.hashset;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.junit.Test;

import com.cheney.collection.Person;

@SuppressWarnings({"rawtypes", "unchecked" })
public class TestHashSet {

	@Test
	public void hashSet() {
		Collection collection=new HashSet();
		Person p=new Person("小明", 12);
		collection.add(p);
		collection.add(new Person("jike",13));
		collection.add(new Person("miki",16));
		collection.add("ABC");
		
		Iterator it=collection.iterator();
		
			while(it.hasNext()){
				System.out.println(it.next());
			}
	}
	
	@Test
	public void linkHashSet() {
		Collection collection=new LinkedHashSet();
		Person p=new Person("小明", 12);
		collection.add(p);
		collection.add(new Person("jike",13));
		collection.add(new Person("miki",16));
		collection.add("ABC");
		
		Iterator it=collection.iterator();
		
			while(it.hasNext()){
				System.out.println(it.next());
			}
	}

}
