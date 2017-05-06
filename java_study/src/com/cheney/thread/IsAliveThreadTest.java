package com.cheney.thread;

public class IsAliveThreadTest extends Thread{
	
	public static void main(String[] args) {
		Thread thread=new IsAliveThreadTest();
		System.out.println(thread.isAlive());//false
		
		thread.start();
		System.out.println(thread.isAlive());//false
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(thread.isAlive());
		
		//已经结束的线程，再调用start()方法会抛异常
//		thread.start();
	}
	
	@Override
	public void run() {
		for(int i=0;i<100;i++){
			System.out.println(getName()+":"+i);
		}
	}
}
