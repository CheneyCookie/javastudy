package com.cheney.collection.map;


import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

import com.cheney.collection.Person;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TestMap {

	@Test
	public void testMap() {
		Map map=new HashMap();
		//1.put(key,value):放入一对键值对
		map.put("CC", new Person("CC",12));
		map.put("BB", new Person("BB",12));
		map.put("AA", new Person("AA",12));
		map.put("DD", new Person("DD",12));
		map.put("EE", new Person("EE",12));
		System.out.println(map.size());
		//2.void clear():清空map
		
		//3.boolean containsKey(Object key):Map中是否包含指定的key,
//		若某个key的equals方法和Map中已有的key比较返回true,则包含
		System.out.println(map.containsKey("CC"));
		//4.boolean containsValue(Object value):Map是否包含指定的value
		System.out.println(map.containsValue(new Person("DD",12)));
		//5.Set<Map.Entry<k,v>> entrySet():得到键值对对应的Entry的Set,
		//需借助于泛型
		
		//6.boolean equals(Object o):比较两个Map是否相等
		
		//7. V get(Object key):根据key返回对应的value
		Object obj=map.get("BB");
		System.out.println(obj);
		//8. boolean isEmpty():检验Map是否为空
		System.out.println(map.isEmpty());
		//9.Set<K> KeySet()，返回Key对应的集合，Set类型
		Set keySet=map.keySet();
		System.out.println(keySet);
		//10.void putAll(Map<? extends K,? extends V> m):放入一组键值对
		Map map2=new HashMap();
		map.put("ONE", "1111");
		map.put("TWO", "2222");
		map.put("THREE", "3333");
		map.putAll(map2);
		//11. V remove(Object key):移除指定键对应的键值对
		map.remove("CC");
		//12. int size():返回Map容量的大小
		System.out.println(map.size());
		//13.Collection<V> values()：返回value值对应的集合
		Collection values =map.values();
		System.out.println(values);
		System.out.println(values.getClass());
		//14.对Map进行遍历
			//遍历键的集合
			//遍历值的集合
			//得到键值对的集合
		
		Iterator it=keySet.iterator();
		while(it.hasNext()){
			Object key=it.next();
			System.out.println(key);
			System.out.println(map.get(key));
			System.out.println();
		}
	}
	
	@Test
	public void TestLinkedHashMap(){
		Map map=new LinkedHashMap();
		//1.put(key,value):放入一对键值对
		map.put("CC", new Person("CC",12));
		map.put("BB", new Person("BB",12));
		map.put("AA", new Person("AA",12));
		map.put("DD", new Person("DD",12));
		map.put("EE", new Person("EE",12));
		System.out.println(map.size());
		
		Set keySet=map.keySet();
		
		Iterator it=keySet.iterator();
		while(it.hasNext()){
			Object key=it.next();
			Object val=map.get(key);
			System.out.println(key);
			System.out.println(val);
			System.out.println();
		}
	}
	
	@Test
	public void TestTreeMap(){
		Map map=new TreeMap();
		
		
		map.put(new Person1("CC",13),"CC");
		map.put(new Person1("BB",11),"BB");
		map.put(new Person1("AA",12),"AA");
		map.put(new Person1("DD",15),"DD");
		map.put(new Person1("EE",14),"EE");
		System.out.println(map.size());
		
		Set keySet=map.keySet();
		
		Iterator it=keySet.iterator();
		while(it.hasNext()){
			Object key=it.next();
			Object val=map.get(key);
			System.out.println(key);
			System.out.println(val);
		}
	}

	@Test
	public void TestTreeMap2(){
		
		
		Comparator comparator=new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				if(o1 instanceof Person && o2 instanceof Person){
					Person p1=(Person) o1;
					Person p2=(Person) o2;
					return p1.getAge()-p2.getAge();
				}
				throw new ClassCastException("不能转为Person类型");
			}
		};
		Map map2=new TreeMap(comparator);
		map2.put(new Person("CC",13),"CC");
		map2.put(new Person("BB",11),"BB");
		map2.put(new Person("AA",12),"AA");
		map2.put(new Person("DD",15),"DD");
		map2.put(new Person("EE",14),"EE");
		System.out.println(map2.size());
		
		Set keySet2=map2.keySet();
		
		Iterator it2=keySet2.iterator();
		while(it2.hasNext()){
			Object key=it2.next();
			Object val=map2.get(key);
			System.out.println(key);
			System.out.println(val);
		}
	}
}
