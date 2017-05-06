package com.cheney.thread;

/**
 * 关于线程：
 * 1.在java中,Thread类代表一个线程。
 * 2.实现线程有两种方式
 * 	1.继承Thread类
 * 	2.实现Runnable接口
 * 3.继承Thread类:
 * 	1.必须重写run()方法:里边放置的是实际线程的线程体
 * 4.启动线程：
 * 	1.创建Thread对象
 * 	2.调用Thread对象的start()方法启动线程，而不是run方法
 * 5.实现Runnable接口的方式
 * 	1.创建实现Runnable接口的实现类：必须实现run()方法
 * 	2.创建1.对应的Runnable接口的实现类对象
 * 	3.创建Thread对象，利用Thread(Runnable target)
 * 	4.调用Thread类的start()方法启动线程
 * 6.线程生命周期相关的几个方法：
 * 	1.yeild():若当前线程调用该方法，则由执行状态变为可运行状态
 * 	2.sleep(int mills):使当前线程休眠一段时间,以毫秒为单位
 * 	3.join():在一个线程中调用另外的线程的join方法，将使当前线程阻塞，等待另一个线程执行完后再进入可执行状态
 * 	4.interrupt()将解除线程的阻塞状态
 * 	5.isAlive():可以判断当前线程是否处于可运行状态或运行状态
 * 7.线程安全的问题：
 * 	1.理解并编写出线程不安全的示例代码：多个线程访问一个共享的资源
 * 	2.使用synchronized代码块解决线程安全的问题：需要在synchronized代码块中
 * 参照共同的一个对象
 * 8.关于线程通信
 * 	1.相关方法：wait(),
 */
public class TestThread {
	
	public static void main(String[] args) {
		//1.创建线程对象
		Thread thread=new FirstThread("FirstThread");
		//2.调用线程对象的start()方法启动线程
		thread.start();
		for(int i=0;i<100;i++){
			String threadName=Thread.currentThread().getName();
			System.out.println(threadName+":"+i);
		}
	}
	
	static class FirstThread extends Thread{
		/**
		 * 线程体在run()方法中
		 */
		public FirstThread(String name) {
			super(name);
		}
		
		@Override
		public void run() {
			String threadName=Thread.currentThread().getName();
			for(int i=0;i<100;i++){
				System.out.println(threadName+":"+i);
			}
		}
	}
}
