package com.cheney.thread;

public class JoinThreadTest extends Thread{
		
	public static void main(String[] args) {
		Thread thread=new JoinThreadTest();
		thread.start();
		
		for(int i=0;i<100;i++){
			System.out.println(Thread.currentThread().getName()+":"+i);
			if(i==10){
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void run() {
		for(int i=0;i<100;i++){
			System.out.println(getName()+":"+i);
		}
	}
}
