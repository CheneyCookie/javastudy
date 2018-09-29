package com.cheney.socket.briup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClientTest {
	public static void main(String[] args) {
		DatagramSocket ds=null;
		try {
//			1、创建客户端的DatagramSocket
			ds=new DatagramSocket();
			
			byte[] buf=new byte[1024];
			InetAddress address=InetAddress.getByName("127.0.0.1");
			
//			2、创建要给服务器发的数据包
			DatagramPacket dp=new DatagramPacket(buf, buf.length, address,10002);
			
//			3、发送数据包服务器
			ds.send(dp);
			
//			4、创建接受服务器端发送过来的包
			dp=new DatagramPacket(buf, buf.length);
			
//			5、接受包
			ds.receive(dp);
			String str=new String(buf);
			System.out.println("client:"+str);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
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
