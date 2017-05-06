package com.cheney.thread;

public class YieldThreadTest extends Thread{
	public static void main(String[] args) {
		Thread thread1=new YieldThreadTest("线程-1");
		Thread thread2=new YieldThreadTest("线程-2");
		
		thread1.start();
		thread2.start();
	}
	
	public YieldThreadTest(String name) {
		super(name);
	}

	@Override
	public void run() {
		for(int i=0;i<100;i++){
			System.out.println(getName()+":"+i);
			
			if(i==10){
				yield();
			}
		}
	}
	
	
}
