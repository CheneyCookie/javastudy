package com.cheney.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启3个线程，这三个线程的ID分别为A、B、C， 每个线程将自己的ID在屏幕上打印10遍，要求输出的结果必须按顺序显示
 * 
 * @author cheney
 *
 */
public class TestABCAlternate {
	public static void main(String[] args) {
		final AlternateDemo ad=new AlternateDemo();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i <= 20; i++) {
					ad.loopA(i);
				}
			}
		},"A").start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i <= 20; i++) {
					ad.loopB(i);
				}
			}
		},"B").start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i <= 20; i++) {
					ad.loopC(i);
					System.out.println("----------");
				}
			}
		},"C").start();
	}
}

class AlternateDemo {
	private int number = 1;// 当前正在执行的线程标记

	private Lock lock = new ReentrantLock();
	private Condition condition1 = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	private Condition condition3 = lock.newCondition();

	public void loopA(int totalLoop) {
		lock.lock();
		try {
			//1.判断
			if(number!=1){
				condition1.await();
			}
			//2.打印
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
			}
			//3.唤醒
			number=2;
			condition2.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void loopB(int totalLoop) {
		lock.lock();
		try {
			//1.判断
			if(number!=2){
				condition2.await();
			}
			//2.打印
			for (int i = 0; i < 15; i++) {
				System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
			}
			//3.唤醒
			number=3;
			condition3.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void loopC(int totalLoop) {
		lock.lock();
		try {
			//1.判断
			if(number!=3){
				condition3.await();
			}
			//2.打印
			for (int i = 0; i < 20; i++) {
				System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
			}
			//3.唤醒
			number=1;
			condition1.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
