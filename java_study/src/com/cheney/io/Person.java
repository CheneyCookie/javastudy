package com.cheney.io;

import java.io.Serializable;

public class Person implements Serializable {

	//类的版本号：用于对象的序列化，
	//具体用于读取对象时比对硬盘上对象的版本和程序中对象的版本是否一致
	//若不一致，读取失败，并抛出异常
	private static final long serialVersionUID = 1L;

	String name;
	Integer age;

	public Person() {
		super();
	}

	public Person(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}

}
