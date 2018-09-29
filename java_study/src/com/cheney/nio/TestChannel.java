package com.cheney.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import org.junit.Test;

/**
 * 一、通道(channel): 用于源节点与目标节点的连接。在Java NIO中负责缓冲区数据的传输。Channel本身
 * 	不存储数据，因此需要配合缓冲区进行传输
 * 
 * 二、通道的一些主要实现类
 * 	java.nio.channels.Channel接口
 * 		|--FileChannel
 * 		|--SocketChannel
 * 		|--ServerSocketChannel
 * 		|--DatagramChannel
 * 
 * 三、获取通道
 * 1. java针对支持通道的类提供了getChannel()方法
 * 		本地IO:
 * 		FileInputStream/FileOutputStream
 * 		RandomAccessFile
 * 	
 * 		网络IO:
 * 		Socket
 * 		ServerSocket
 * 		DatagramSocket
 * 
 * 2. 在JDK1.7中的NIO.2针对各个通道提供了静态方法open()
 * 
 * 3. 在JDK1.7中的NIO.2的Files工具类的newByteChannel()
 * 
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo
 * 
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取(Scattering Reads): 将通道中的数据分散到多个缓冲区中
 * 聚集写入(Gathering Writes): 将缓冲区中的数据聚集到通道中
 * 
 * 六、字符集:CharSet
 * 编码: 字符串  -> 字节数组
 * 解码: 字节数组 -> 字符串
 * 
 * @author cheney
 *
 */
public class TestChannel {
	
	@Test
	public void test6() throws CharacterCodingException {
		Charset cs1 = Charset.forName("GBK");
		
		//获取编码器
		CharsetEncoder ce = cs1.newEncoder();
		
		//获取解码器
		CharsetDecoder cd = cs1.newDecoder();
		
		CharBuffer cBuf = CharBuffer.allocate(1024);
		cBuf.put("我爱凌秋萍！");
		cBuf.flip();
		
		//编码
		ByteBuffer bBuf = ce.encode(cBuf);
		
		for (int i = 0; i < bBuf.limit(); i++) {
			System.out.println(bBuf.get());
		}
		
		bBuf.flip();
		CharBuffer cBuf2 = cd.decode(bBuf);
		System.out.println(cBuf2.toString());
	}
	
	@Test
	public void test5() {
		SortedMap<String, Charset> map = Charset.availableCharsets();
		Set<Entry<String,Charset>> entrySet = map.entrySet();
		
		for (Entry<String, Charset> entry : entrySet) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
	
	@Test
	public void test4() throws IOException {
		RandomAccessFile raf1 = new RandomAccessFile("hello.txt", "rw");
		
		//1.获取通道
		FileChannel channel1 = raf1.getChannel();
		
		//2.分配指定大小的缓冲区
		ByteBuffer buf1 = ByteBuffer.allocate(20);
		ByteBuffer buf2 = ByteBuffer.allocate(1024);
		
		//3.分散读取
		ByteBuffer[] bufs = {buf1, buf2};
		channel1.read(bufs);
		
		for (ByteBuffer byteBuffer : bufs) {
			byteBuffer.flip();
		}
		
		System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
		System.out.println("------------");
		System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));
		
		//聚集写入
		RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
		FileChannel channel2 = raf2.getChannel();
		
		channel2.write(bufs);
		
		raf1.close();
		raf2.close();
	}
	
	//通道之间的数据传输(直接缓冲区)
	@Test
	public void test3() throws IOException {
		FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
		
//		inChannel.transferTo(0, inChannel.size(), outChannel);
		outChannel.transferFrom(inChannel, 0, inChannel.size());
		
		inChannel.close();
		outChannel.close();
	}
	
	//2. 使用直接缓冲区完成文件的复制(内存映射文件)
	@Test
	public void test2() throws IOException {
		long startTime = System.currentTimeMillis();
		
		FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
		FileChannel outChannel = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
		
		//内存映射文件
		MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
		MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
		
		//直接对缓冲区进行数据的读写操作
		byte[] dst = new byte[inMappedBuf.limit()];
		inMappedBuf.get(dst);
		outMappedBuf.put(dst);
		
		inChannel.close();
		outChannel.close();
		
		long endTime = System.currentTimeMillis();
		System.out.println("耗费时间为： " + (endTime - startTime));
	}
	
	//1. 利用通道完成文件的复制(非直接缓冲区)
	@Test
	public void test1() {
		long startTime = System.currentTimeMillis();
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		//1. 获取通道
		FileChannel inChannel = null;
		FileChannel outChannel = null;
		try {
			fis = new FileInputStream("1.jpg");
			fos = new FileOutputStream("2.jpg");
			
			inChannel = fis.getChannel();
			outChannel = fos.getChannel();
			
			//2. 分配指定大小的缓冲区
			ByteBuffer buf = ByteBuffer.allocate(1024);
			
			//3. 将通道中的数据存入缓冲区中
			while (inChannel.read(buf) != -1) {
				//切换成读取数据的模式
				buf.flip();
				//4.将缓冲区中的数据写入通道中
				outChannel.write(buf);
				//清空缓冲区
				buf.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outChannel != null) {
					outChannel.close();
				}
				if (inChannel != null) {
					inChannel.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("耗费时间为： " + (endTime - startTime));
	}
}
