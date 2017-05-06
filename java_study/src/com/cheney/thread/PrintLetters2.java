package com.cheney.thread;

public class PrintLetters2 implements Runnable {

	private char c = 'a';
	
	public static void main(String[] args) {
		PrintLetters2 printLetters2=new PrintLetters2();
		Thread thread1=new Thread(printLetters2);
		Thread thread2=new Thread(printLetters2);
		
		thread1.setName("线程-1");
		thread2.setName("线程-2");
		
		thread1.start();
		thread2.start();
	}

	public boolean print() {
		synchronized (this) {
			
			if (c <= 'z') {
				System.out.println(Thread.currentThread().getName()+":"+c);
				c++;
				
				notify();
				
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return true;
			}
			return false;
		}
	}

	@Override
	public void run() {
		boolean flag=print();
		while(flag){
			flag=print();
		}
	}
}
