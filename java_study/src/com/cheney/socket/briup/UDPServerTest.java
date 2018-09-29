package com.cheney.socket.briup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServerTest {
	public static void main(String[] args) {
//		1、创建服务器端DataGramSocket
		DatagramSocket ds=null;
		try {
			ds=new DatagramSocket(10002);
//			2、创建接受客户端发送过来的 数据包
			byte[] buf=new byte[1024];
			DatagramPacket dp=new DatagramPacket(buf, buf.length);
			System.out.println("Server wait");
			
//			3、接受数据包
			ds.receive(dp);
			System.out.println("Server receive");
			
//			4、获取客户端的ip port
			InetAddress add=dp.getAddress();
			String host=add.getHostName();
			int port=dp.getPort();
			System.out.println("ip:"+host+"port:"+port);
			
//			5、发送给客户端的数据包
			dp=new DatagramPacket(buf, buf.length,add,port);
			buf="Hello World".getBytes();
//			6、发数据包
			ds.send(dp);
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(ds!=null){
				ds.close();
			}
		}
		
	}
}
