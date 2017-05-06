package com.cheney.thread;

public class PriorityThread extends Thread{

	public static void main(String[] args) {
		Thread thread1=new PriorityThread("thread-1");
		Thread thread2=new PriorityThread("thread-2");
		
		System.out.println(thread1.getPriority());
		System.out.println(thread2.getPriority());
		System.out.println(Thread.currentThread().getPriority());
		
		thread1.setPriority(MIN_PRIORITY);
		thread2.setPriority(MAX_PRIORITY);
		
		thread1.start();
		thread2.start();
		
		for(int i=0;i<100;i++){
			System.out.println("main:"+i);
		}
	}
	
	public PriorityThread(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		for(int i=0;i<100;i++){
			System.out.println(getName()+":"+i);
		}
	}
}
