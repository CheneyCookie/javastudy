package com.cheney.thread;

public class PrintNumber {
	int i;
	
	public static void main(String[] args) {
//		int i=0;
		
		PrintNumber printNumber=new PrintNumber();
		NumberThread thread1=new NumberThread("线程-1",printNumber);
		NumberThread thread2=new NumberThread("线程-2",printNumber);
		
//		thread1.setI(i);
		
		thread1.start();
		thread2.start();
	}
}

class NumberThread extends Thread{
	PrintNumber printNumber;
	public NumberThread(String threadName,PrintNumber printNumber) {
		super(threadName);
		this.printNumber=printNumber;
	}
	
//	private static int i;
//	public void setI(int i) {
//		NumberThread.i = i;
//	}
	@Override
	public void run() {
		for(;printNumber.i<100;printNumber.i++){
			System.out.println(getName()+":"+printNumber.i);
		}
	}
}
