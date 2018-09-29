package com.cheney.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1.ReadWriteLock：读写锁
 * 
 * 写写/读写 需要"互斥"
 * 读读 不需要互斥
 * 
 * @author cheney
 *
 */
public class TestReadWriteLock {
	public static void main(String[] args) {
		final ReadWriteLockDemo rw=new ReadWriteLockDemo();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				rw.set((int)(Math.random()*100));
			}
		},"Write:").start();
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					rw.get();
				}
			}).start();
		}
	}
}

class ReadWriteLockDemo{
	private int number =0;
	private ReadWriteLock lock=new ReentrantReadWriteLock();
	
	public  void get(){
		lock.readLock().lock();//上锁
		try{
			System.out.println(Thread.currentThread().getName()+":"+number);
		}finally{
			lock.readLock().unlock();
		}
	}
	
	public void set(int number){
		lock.writeLock().lock();
		try{
			System.out.println(Thread.currentThread().getName());
			this.number=number;
		}finally{
			lock.writeLock().unlock();
		}
	}
}