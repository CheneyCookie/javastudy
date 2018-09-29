package com.cheney.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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
public class TestBlockingNIO {

	// 客户端
	@Test
	public void client() {
		SocketChannel socketChannel = null;
		FileChannel inChannel = null;
		try {
			// 1.获取通道
			socketChannel = SocketChannel
					.open(new InetSocketAddress("127.0.0.1", 9898));

			inChannel = FileChannel.open(Paths.get("1.jpg"),
					StandardOpenOption.READ);

			// 2.分配指定大小的缓冲区域
			ByteBuffer buf = ByteBuffer.allocate(1024);

			// 3.读取本地文件，并发送到服务端
			while (inChannel.read(buf) != -1) {
				buf.flip();
				socketChannel.write(buf);
				buf.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inChannel != null) {
					inChannel.close();
				}
				if (socketChannel != null) {
					socketChannel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void server() {
		//1.获取通道
		ServerSocketChannel serverSocketChannel = null;
		FileChannel fileChannel = null;
		SocketChannel socketChannel = null;
		
		try {
			//1.获取通道
			serverSocketChannel = ServerSocketChannel.open();
			fileChannel = FileChannel.open(Paths.get("5.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
			//2.绑定连接
			serverSocketChannel.bind(new InetSocketAddress(9898));
			
			//3.获取客户端连接的通道
			socketChannel = serverSocketChannel.accept();
			
			//4.分配指定大小的缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			//5.接受客户端的数据，并保存到本地
			while (socketChannel.read(buf) != -1) {
				buf.flip();
				fileChannel.write(buf);
				buf.clear();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			//6.关闭通道
			try {
				if (socketChannel != null) {
					socketChannel.close();
				}
				if (fileChannel != null) {
					fileChannel.close();
				}
				if (serverSocketChannel != null) {
					serverSocketChannel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
