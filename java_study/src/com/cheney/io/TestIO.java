package com.cheney.io;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;

import org.junit.Test;

public class TestIO {

	@Test
	public void testFile() throws IOException {
		File file=new File("hello.txt");
		String name=file.getName();
		System.out.println(name);
		
		//获取绝对路径
		String path=file.getAbsolutePath();
		System.out.println(path);
		
		//为文件重命名
//		file.renameTo(new File("c:\\hello.txt"));
		
		//文件检测的相关方法
		System.out.println(file.exists());
		File dir=new File("cheney");
		System.out.println(dir.isFile());
		
		//获取文件的常规信息
		System.out.println(file.length());
		
		//文件操作相关
		File file2=new File("abcd.txt");
		file2.createNewFile();
	}
	
	//测试字节输入流
	@Test
	public void testInputStream() throws IOException{
		//创建一个字节输入流
		InputStream in=new FileInputStream("hello.txt");
		
		//读取文件的内容
		//读取第一个字节,效率很低，不建议,-1表示读完
//		int result=in.read();
//		while(result!=-1){
//			System.out.print((char)result);
//			result=in.read();
//		}
		//System.out.println(result);
		//System.out.println((char)result);
		
		//一次读一组：一组字符
//		byte[] buffer=new byte[10];
//		int len=0;
//		//返回读取字节数，若为-1表示读取到文件的结尾
//		while((len=in.read(buffer))!=-1){
//			for(int i=0;i<len;i++){
//				System.out.print((char)buffer[i]);
//			}
//		}
		
		//把内容读取到字节数组的部分连续元素中
		byte[] result=new byte[1024*10];
		in.read(result,10,in.available());
		
		//关闭流资源
		in.close();
	}
	
	//测试字符输入流
	@Test
	public void testReader() throws IOException{
		//利用字符输入流读取hello.txt文档的内容，输出到控制台
		Reader reader=new FileReader("hello.txt");
		
		char[] buffer=new char[10];
		int len=0;
		
		while((len=reader.read(buffer))!=-1){
			for(int i=0;i<len;i++){
				System.out.print(buffer[i]);
			}
		}
		
		reader.close();
	}
	
	@Test
	public void testOutputStream() throws IOException{
		OutputStream out=new FileOutputStream("abcde.txt");
		
		String content="hello java what!";
//		int len=10;
//		byte[] contentBytes=content.getBytes();
//		for(int i=0;i<content.length()/10;i++){
//			//把String拆分成多个buffer
//			out.write(contentBytes,i*10,len);
//		}
//		
//		if(content.length()%10!=0){
//			out.write(contentBytes,10*(content.length()/10), content.length()-(10*(content.length()/10)));
//		}
		
		out.write(content.getBytes());
		
		out.close();
	}
	
	
	//典型，通常情况求这样使用
	@Test
	public void testCopyFile() throws IOException{
		//1.创建定位到hello.txt的文件的输入流
		InputStream in=new FileInputStream("hello.txt");
		//2.创建定位到hello2.txt的文件的输出流
		OutputStream out=new FileOutputStream("hello2.txt");
		//3.创建一个byte数组，用于读写文件
		byte[] buffer=new byte[1024*10];
		int len=0;
		//4.读写文件
		if((len=in.read(buffer))!=-1){
			out.write(buffer,0,len);
		}
		//5.关闭流资源
		out.close();
		in.close();
	}
	
	@Test
	public void testCopyByReaderAndWriter() throws IOException{
		//1.创建字符输入输出流
		Reader reader= new FileReader("hello.txt");
		Writer writer=new FileWriter("hello3.txt");
		//2.创建一个字符数组
		char[] buffer=new char[10];
		//3.利用循环读取源文件，并向目标文件写入
		int len=0;
		while((len=reader.read(buffer))!=-1){
			writer.write(buffer, 0, len);
			System.out.println(len);
		}
		//4.注意，使用写入的方法，为write(char[] buf,int off,int len)
		//而不能直接使用write(char[] buf)
		//.关闭流资源
		reader.close();
		writer.close();
		
	}
	
	/**
	 * 更高效率
	 * 复制hello.txt为hello4.txt
	 * @throws IOException 
	 */
	@Test
	public void testBufferedReaderAndBufferedWriter() throws IOException{
		//1.创建BufferedReader和BufferedWriter
		Reader in=new FileReader("hello.txt");
		BufferedReader bufferedReader=new BufferedReader(in);
		
		Writer out=new FileWriter("hello4.txt");
		BufferedWriter bufferedWriter=new BufferedWriter(out);
		
		//2.进行读写操作
		String str=null;
		
		int i=0;
		
		while((str=bufferedReader.readLine())!=null){
			if(i!=0){
				bufferedWriter.write("\n");
			}
			bufferedWriter.write(str);
			i++;
		}
		
		
		//3.关闭IO流;直接关闭包装流，内部会关闭节点流
		bufferedWriter.close();
		bufferedReader.close();
	}
	
	@Test
	public void testBufferedInputStreamAndBufferedOutputStream() throws IOException{
		InputStream in=new FileInputStream("hello.txt");
		BufferedInputStream bufferedInputStream=new BufferedInputStream(in);
		
		OutputStream out=new FileOutputStream("hello5.txt");
		BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(out);
		
		byte[] buffer=new byte[1024];
		int len=0;
		
		while((len=bufferedInputStream.read(buffer))!=-1){
			bufferedOutputStream.write(buffer, 0, len);
		}
		
		bufferedInputStream.close();
		bufferedOutputStream.close();
	}
	
	@Test
	public void testInputStreamReader() throws IOException{
		//指向文档的字节流
		InputStream in=new FileInputStream("hello.txt");
		//把上面的流转化为字符流
		Reader reader=new InputStreamReader(in);
		//把字符流转化为带缓冲的字符流
		BufferedReader bufferedReader=new BufferedReader(reader);
		
		String str=null;
		while((str=bufferedReader.readLine())!=null){
			System.out.println(str);
		}
		
		in.close();
		reader.close();
		bufferedReader.close();
	}
	
	/*
	 * 创建两个字节输入输出流：分别指向hello.txt,hello6.txt
	 * 然后再指向字符输入输出流
	 * 再转为带缓存的字符输入输出流
	 * 
	 * 完成文件的复制
	 * */
	@Test
	public void testOutputStreamReader() throws IOException{
		InputStream in=new FileInputStream("hello.txt");
		OutputStream out=new FileOutputStream("hello6.txt");
		
		Reader reader=new InputStreamReader(in);
		Writer writer=new OutputStreamWriter(out);
		
		BufferedReader bufferedReader=new BufferedReader(reader);
		BufferedWriter bufferedWriter=new BufferedWriter(writer);
		
		String str=null;
		int i=0;
				
		while((str=bufferedReader.readLine())!=null){
			if(i!=0){
				bufferedWriter.write("\n");
			}
			bufferedWriter.write(str);
			i++;
		}
		
		bufferedWriter.close();
		bufferedReader.close();
		
	}
	
	@Test
	public void testSerializable() throws IOException{
		Person person=new Person("AA",12);
		
		//使用ObjectOutputStream把对象写到硬盘上
		OutputStream out=new FileOutputStream("d:\\person.txt");
		ObjectOutputStream objectOutputStream=new ObjectOutputStream(out);
		
		objectOutputStream.writeObject(person);
		
		objectOutputStream.close();
		
	}
	
	@Test
	public void testInputObjectStream() throws IOException, ClassNotFoundException{
		InputStream in=new FileInputStream("d:\\person.txt");
		
		ObjectInputStream objectInputStream=new ObjectInputStream(in);
		Object obj=objectInputStream.readObject();
		System.out.println(obj);
		
		objectInputStream.close();
	}
	
	@Test
	public void testRandomAccessFile() throws IOException{
		
		//1.创建RandomAccessFile类
		RandomAccessFile access=new RandomAccessFile("hello.txt", "rw");
		
		//2.对文件进行读写操作
		String str=null;
		while((str=access.readLine())!=null){
			System.out.println(str);
		}
		
		//向文件结尾写入cheney
//		access.writeChars("cheney");
		//设置指针位置；向指定位置写入字符串：把原有的内容覆盖了
		access.seek(20);
		access.writeBytes("cheney");
		
		//3.关闭RandomAccessFile对象
		access.close();
	}
	
	@Test
	public void testRandomAccessFile2() throws IOException{
		RandomAccessFile access=new RandomAccessFile("hello.txt","rw");
		
		//先读一行
		String line=access.readLine();
		
		//把第一行后面的内容先读取到一个byte数组中
		byte[] buffer=new byte[(int)access.length()-line.length()];
		access.read(buffer);
		
		//移动指针到第一行的后面
		access.seek(line.length());
		
		//写入要写的字符串
		access.writeBytes("\ncheney\n");
		//再写入先前的内容
		access.write(buffer);
		
		access.close();
	}

}
