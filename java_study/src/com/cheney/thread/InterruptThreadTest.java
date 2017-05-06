package com.cheney.thread;

public class InterruptThreadTest extends Thread{
	
	public static void main(String[] args) {
		InterruptThreadTest interruptThreadTest=new InterruptThreadTest();
		interruptThreadTest.start();
		interruptThreadTest.interrupt();
	}
	
	@Override
	public void run() {
		for(int i=0;i<100;i++){
			System.out.println(getName()+":"+i);
			if(i==10){
				try {
					sleep(100000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
