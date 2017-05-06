package com.cheney.string;


import org.junit.Test;

/*
 * StringBuffer和StringBuilder：是可以被修改的字符序列
 * 1.append()方法：把字符串加入到已有的字符串序列后面
 * 注意append方法的返回值还是当前StringBuilder对象,可以使用方法的连缀
 * 
 * 2.StringBuffer VS StringBuilder
 * StringBuilder是线程不安全的，效率更高，所以更多使用StringBuilder
 * StringBuffer是线程安全的，效率偏低，在多线程情况下使用
 * */
public class TestStringBufferAndStringBuilder {

	@Test
	public void testStringBuffer() {
		StringBuffer stringBuffer=new StringBuffer("abcde");
		stringBuffer.replace(1, 3, "mvp");
		System.out.println(stringBuffer);
	}
	
	@Test
	public void testAppend(){
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("<html>")
					.append("<body>")
					.append("<>/body")
					.append("</html>");
		System.out.println(stringBuilder);
	}

}
