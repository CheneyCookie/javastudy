package com.cheney.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

/**
 * 一、使用NIO完成网络通信的三个核心:
 * 
 * 1.通道(channel1): 负责连接 
 * 		java.nio.channels.Channel 接口:
 * 	 		|--SelectableChannel
 * 			|--SocketChannel 
 * 			|--ServerSocketChannel 
 * 			|--DatagramChannel
 * 
 * 			|--Pipe.SinkChannel 
 * 			|--Pipe.sourceChannel
 * 
 * 2.缓冲区(Buffer): 负责数据的存取
 * 
 * 3.选择器(Selector): 是SelectableChannel的多路复用器。用于监控SelectableChannel的IO状况
 * 
 * @author cheney
 *
 */
public class TestNonBlockingNIO {

	// 客户端
	@Test
	public void client() {
		SocketChannel socketChannel = null;
		try {
			//1. 获取通道
			socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
			//2. 切换非阻塞模式
			socketChannel.configureBlocking(false);
			//3.分配指定大小缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);
			//4.发送数据给服务端
			Scanner sc = new Scanner(System.in);
			while (sc.hasNext()) {
				String str = sc.next();
				buf.put((new Date().toString() + "\n" + str).getBytes());
				buf.flip();
				socketChannel.write(buf);
				buf.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socketChannel != null) {
					socketChannel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//服务端
	@Test
	public void server() {
		//1. 获取通道
		ServerSocketChannel serverSocketChannel = null;
		try {
			//1.获取通道
			serverSocketChannel = ServerSocketChannel.open();
			
			//2.切换非阻塞模式
			serverSocketChannel.configureBlocking(false);
			
			//3.绑定连接
			serverSocketChannel.bind(new InetSocketAddress(9898));
			
			//4.获取选择器
			Selector selector = Selector.open();
			
			//5.将通道注册到选择器上，并指定“监听接收事件”
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			//6轮询式的获取选择器上已经“准备就绪”的事件
			while (selector.select() > 0) {
				//7.获取当前选择器中所有注册的“选择键(已就绪的监听事件)”
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				
				while (it.hasNext()) {
					//8.获取准备“就绪”的事件
					SelectionKey sk = it.next();
					
					//9.判断具体是什么事件准备就绪
					if (sk.isAcceptable()) {
						//10.若“接收就绪”，获取客户端连接
						SocketChannel socketChannel = serverSocketChannel.accept();
						
						//11.切换非阻塞模式
						socketChannel.configureBlocking(false);
						
						//12.将该通道注册到选择器上
						socketChannel.register(selector, SelectionKey.OP_READ);
					} else if (sk.isReadable()) {
						//13.获取当前选择器上“读就绪”状态的通道
						SocketChannel socketChannel = (SocketChannel) sk.channel();
						
						//14.读取数据
						ByteBuffer buf = ByteBuffer.allocate(1024);
						
						int len = 0;
						while ((len = socketChannel.read(buf)) > 0) {
							buf.flip();
							System.out.println(new String(buf.array(), 0, len));
							buf.clear();
						}
					}
					//.取消选择键SelectionKey
					it.remove();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
