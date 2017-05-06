package com.cheney.socket;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.junit.Test;

/**
 * 1.IP和端口的具体意义
 * 	1.IP定位网络中的一台主机
 * 	2.端口定位主机的一个网络程序
 * 2.InetAddress:对象表示网络中的一个地址
 * InetAddress address=InetAddress.getByName("127.0.0.1");
 * 3.TCP/IP编程：
 * 	1.服务器/客户端：客户端发送请求到服务器
 * 	2.ServerSocket
 * 	3.Socket
 */
public class TestSocket {

	/*
	 * InetAddress:表示互联网（或局域网）的一台主机地址
	 * */
	@Test
	public void testInetAddress() throws UnknownHostException {
		InetAddress address=InetAddress.getByName("www.hao123.com");
		//www.hao123.com/61.135.162.10
		System.out.println(address);
		
		InetAddress address2=InetAddress.getLocalHost();
		System.out.println(address2);
	}
	
	@Test
	public void testServerSocket() throws IOException{
		//创建ServerSocket对象
		ServerSocket serverSocket=new ServerSocket(8989);
		//接受客户端的请求，并得到Socket对象
		Socket socket=serverSocket.accept();
		
		//通过输入输出流和客户端进行交互
		OutputStream out=socket.getOutputStream();
		PrintWriter writer=new PrintWriter(out);
		writer.write("来自服务器端的问候");
		
		writer.close();
		out.close();
		
		//关闭socket资源
		socket.close();
		serverSocket.close();
	}

	@Test
	public void testSocket() throws IOException{
		InetAddress address=InetAddress.getByName("127.0.0.1");
		//创建Socket对象：同时也向服务器端发出请求
		Socket socket=new Socket(address,8989);
		
		//通过输入输出流和服务器端进行交互
		InputStream in=socket.getInputStream();
		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
		System.out.println("^_^"+reader.readLine());
		
		reader.close();
		in.close();
		//关闭socket资源
		socket.close();
	}
	
	@Test
	public void testServiceSocket2() throws IOException{
		ServerSocket serverSocket=new ServerSocket(8686);
		Socket socket=serverSocket.accept();
		
		InputStream in=new FileInputStream("abc.jpg");
		byte[] buffer=new byte[1024];
		int len=0;
		
		OutputStream out=socket.getOutputStream();
		while((len=in.read(buffer))!=-1){
			out.write(buffer, 0, len);
		}
		
		out.close();
		in.close();
		
		socket.close();
		serverSocket.close();
	}
	
	@Test
	public void testClientSocket2() throws IOException{
		InetAddress address=InetAddress.getByName("127.0.0.1");
		Socket socket=new Socket(address,8686);
		
		InputStream in=socket.getInputStream();
		OutputStream out=new FileOutputStream("c:\\abcd.jpg");
		
		byte[] buffer =new byte[1024];
		int len=0;
		
		while((len=in.read(buffer))!=-1){
			out.write(buffer,0,len);
		}
		
		in.close();
		out.close();
		
		socket.close();
	}
	
	@Test
	public void testURL() throws IOException{
		URL url=new URL("http://127.0.0.1:8888/abcd.txt?name=Tom");
		
		System.out.println(url.getPath());
		System.out.println(url.getQuery());
		
		URLConnection urlConnection=url.openConnection();
		System.out.println(urlConnection);

		InputStream in=urlConnection.getInputStream();
		OutputStream out=new FileOutputStream("test.txt");

		byte[] buffer=new byte[1024];
		int len=0;
		
		while((len=in.read(buffer))!=-1){
			out.write(buffer, 0, len);
		}
		
		in.close();
		out.close();
	}
}
