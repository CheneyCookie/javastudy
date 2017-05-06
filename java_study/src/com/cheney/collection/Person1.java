package com.cheney.collection;

@SuppressWarnings("rawtypes")
public class Person1 implements Comparable
{
	private String name;
	private int age;

	
	public Person1() {
		super();
	}

	public Person1(String name,int age){
		this.name=name;
		this.age=age;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name=name;
	}

	public int getAge(){
		return age;
	}

	public void setAge(int age){
		this.age=age;
	}

	@Override
	public String toString(){
		return "Person [name="+name+", age="+age+"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person1 other = (Person1) obj;
		if (age != other.age)
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof Person1){
			Person1 person=(Person1) o;
			//按名字排序
			//return this.name.compareTo(person.name);
			//按年龄排序升序
			//return this.age-person.age;
			//降序
			return -this.age+person.age;
		}else{
			throw new ClassCastException("不能转为Person类型！");
		}
	}

}