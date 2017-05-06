package com.cheney.thread;

public class ShareApple implements Runnable {

	private int appleCount = 5;

	public static void main(String[] args) {

		ShareApple shareApple = new ShareApple();

		Thread thread1 = new Thread(shareApple);
		Thread thread2 = new Thread(shareApple);

		thread1.setName("小明");
		thread2.setName("小强");

		thread1.start();
		thread2.start();
	}

	boolean getApple() {
		synchronized (this) {

			if (appleCount > 0) {
				appleCount--;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()
						+ "拿走了一个苹果。" + "还剩下" + appleCount + "个苹果");
				return true;
			}
			return false;
		}
	}

	@Override
	public void run() {
		boolean flag = getApple();
		while (flag) {
			flag = getApple();
		}
		System.out.println(Thread.currentThread().getName() + "线程结束了");
	}

}
