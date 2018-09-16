package com.cheney.string;


import org.junit.Test;

/*
 * 1.String 是不可变的字符序列
 * 2.关于字符串缓冲池:直接通过=为字符串赋值，会先在缓冲池中查找有没有对应的字符串，
 * 如果没有就把那个引用赋给字符串变量，否则会创建一个新的字符串，并把对应的字符串放入到缓冲池当中
 * 3.字符串的几个常用方法：
 * 		1.去除前后空格的trim()方法
 * 		2.求子字符串的方法：subString(fromIndex)
 * 		subString(fromIndex):从fromIndex开始，包含fromIndex，且String的字符索引从0开始
 * 		subString(fromIndex,toIndex):[fromIndex,toIndex)
 * 		indexOf:求指定字符的索引
 * 		charAt:求指定位置的字符
 * 		split(String regex):把字符串拆分成字符串数组
 * 		equals():比较字符串内容是否相等必须使用该方法，而不能直接使用==
 * */
public class TestString {

	@Test
	public void test() {
		String str="www.cheney.com";
		String result=str.replace('c', 'l');
		//str=str.replace('c', 'l');
		
		System.out.println(str);
		System.out.println(result);
	}
	
	@Test
	public void testPassRef(){
		Person person=new Person("ABC",12);
		System.out.println(person);
		changePerson(person);
		System.out.println(person);
		String str="abcd";
		System.out.println(str);
		changeString(str);
		System.out.println(str);
	}
	
	public void changePerson(Person person){
		person.setName("cheney");
	}
	
	public void changeString(String str){
		String result=str.replace('a', 'b');
		System.out.println(result);
		System.out.println(str);
	}
	
	@Test
	public void testNewString(){
		String str1="hello world";
		String str2="hello world";
		System.out.println(str1==str2);//true
		System.out.println(str1.hashCode());
		System.out.println(str2.hashCode());
		
		String str3=new String("abcde");
		String str4=new String("abcde");
		System.out.println(str3==str4);//false
	}
	
	@Test
	public void testTrim(){
		String str="   ab  cd  ";
		System.out.println("--"+str+"--");
		
		String str2=str.trim();
		System.out.println("--"+str+"--");
		System.out.println("--"+str2+"--");
	}
	
	@Test
	public void testSubString(){
		String str="http://www.cheney.com/index.jsp?name=Tom";
		
		String str1=str.substring(7);
		System.out.println(str1);
		
		String str2=str.substring(1, 5);
		System.out.println(str2);
	}
	
	@Test
	public void testCharAT(){
		String str="http://www.cheney.com/index.jsp?name=Tom";
		System.out.println(str.charAt(5));
	}
	
	@Test
	public void testIndexOf(){
		String str="http://www.cheney.com/index.jsp?name=Tom";
		System.out.println(str.indexOf("//"));//5
		System.out.println(str.lastIndexOf("/"));//22
		
		int beginIndex=str.indexOf("//")+2;
		int endIndex=str.lastIndexOf("/");
		System.out.println(str.substring(beginIndex, endIndex));
	}

	@Test
	public void testSplit(){
		String str="a-b-c-d";
		String[] values=str.split("-");
		
		for(String s:values){
			System.out.println(s);
		}
	}
	
}
