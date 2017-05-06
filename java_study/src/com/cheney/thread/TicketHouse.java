package com.cheney.thread;

public class TicketHouse implements Runnable {

	private int fiveCount = 1, twentyCount = 0;

	public static void main(String[] args) {
		Runnable runnable = new TicketHouse();
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		Thread thread3 = new Thread(runnable);

		thread1.setName("张飞");
		thread2.setName("关羽");
		thread3.setName("刘备");

		thread1.start();
		thread2.start();
		thread3.start();
	}

	public synchronized void buy() {
		String name = Thread.currentThread().getName();

		// 张飞：20元
		if ("张飞".equals(name)) {
			if (fiveCount < 3) {
				try {
					System.out.println("五元面值："+fiveCount+",张飞请先等待");
					wait();
					twentyCount++;
					System.out.println("五元面值："+fiveCount+",卖一张票给张飞，找零15。");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else if ("关羽".equals(name)) {
			fiveCount++;
			System.out.println("卖一张票给关羽，钱刚好。五元面值："+fiveCount);
		} else if ("刘备".equals(name)) {
			fiveCount++;
			System.out.println("卖一张票给刘备，钱刚好.五元面值："+fiveCount);
		}
		if(fiveCount==3){
			notifyAll();
		}
	}

	@Override
	public void run() {
		buy();
	}

}
