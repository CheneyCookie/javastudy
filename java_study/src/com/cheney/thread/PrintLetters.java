package com.cheney.thread;

public class PrintLetters implements Runnable {

	private char c = 'a';

	public static void main(String[] args) {

		PrintLetters printLetters = new PrintLetters();

		Thread thread1 = new Thread(printLetters);
		Thread thread2 = new Thread(printLetters);

		thread1.setName("线程1");
		thread2.setName("线程2");

		thread1.start();
		thread2.start();
	}

	public boolean print() {
		synchronized (this) {

			if (c <= 'z') {
				System.out.println(Thread.currentThread().getName() + ":" + c);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				c++;
				return true;
			}
			return false;
		}
	}

	@Override
	public void run() {
		boolean flag = print();
		while (flag) {
			flag = print();
		}
	}

}
