package com.cheney.proxy;

import java.util.HashMap;
import java.util.Map;

public class ServiceImpl implements Service {

	private static Map<Integer, Person> persons = new HashMap<Integer, Person>();

	public static Map<Integer, Person> getPersons() {
		return persons;
	}
	
	public ServiceImpl() {
		persons.put(1001, new Person(1001, "AA"));
		persons.put(1002, new Person(1002, "BB"));
	}

	@Override
	public void addNew(Person person) {
		persons.put(person.getId(), person);
	}

	@Override
	public void delete(Integer id) {
		if(id==1001){
			throw new RuntimeException("1001不能被删除");
		}
		persons.remove(id);
	}

	@Override
	public void update(Person person) {
		persons.put(person.getId(), person);
	}

}
