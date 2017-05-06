package com.cheney.collection;

import com.cheney.collection.CollectionTest;

public class Test
{
	public static void main(String[] args){
		CollectionTest coll=new CollectionTest();
		coll.test1();
		System.out.println();

		coll.test2();
		
		coll.testIterator();

		coll.removeOrClear();

		coll.removeAndRetainAll();
	}

}