package com.cheney.reflection;

public class Person {
	String name;
	private int age;

	private void test(){}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	@AgeValidator(min=18,max=35)
	public void setAge(int age) {
		this.age = age;
	}

	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
		System.out.println("有参数构造器");
	}

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
	
	private String method2(){
		return "private String method2";
	}
	
	private Person method3(String name,Integer age){
		Person person=new Person(name,age);
		return person;
	}

}
