package com.cheney.thread;

public class MyRunnable implements Runnable{
	int i=0;
	
	@Override
	public void run() {
		for(;i<100;i++){
			System.out.println(Thread.currentThread().getName()+":"+i);
		}
	}
	public static void main(String[] args) {
		MyRunnable mr=new MyRunnable();
		Thread thread1=new Thread(mr);
		Thread thread2=new Thread(mr);
		 
		thread1.start();
		thread2.start();
	}
}
