package com.hinz.leetcode.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 比较不同锁机制的速度
 * 结论：synchronized最慢，semaphore与reentrantLock相近，atomic在自加自减方面最快
 * @author hinzzz
 * @date 2021/9/8 16:43
 * @desc
 */
public class CompareLocks {
	static int threadNum = 8;
	static int addTime = 1000000; // 自加次数
	static int countWithSema = 0;
	static int countWithSyn = 0;
	static int countWithReen = 0;
	static AtomicInteger countWithAtomic = new AtomicInteger(0);
	static final Semaphore semaLock = new Semaphore(1);
	static final Object synLock = new Object();
	static final ReentrantLock reenLock = new ReentrantLock();

	public static void compareBetweenSemaphoreAndSyn() {
		// four latches ensure adding to 1000000 then get the spending time
		CountDownLatch latch1 = new CountDownLatch(addTime);
		CountDownLatch latch2 = new CountDownLatch(addTime);
		CountDownLatch latch3 = new CountDownLatch(addTime);
		CountDownLatch latch4 = new CountDownLatch(addTime);
		// thread pool
		ExecutorService executor = Executors.newFixedThreadPool(threadNum);
		// synchronized
		Long startTime1 = System.currentTimeMillis();
		for (int i = 0; i < addTime; i++) {
			executor.execute(() -> {
				addWithSyn();
				latch1.countDown();
			});
		}
		try {
			latch1.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("time taked by synchronized: " + (System.currentTimeMillis() - startTime1));
		System.out.println(countWithSyn);
		// semaphore
		Long startTime2 = System.currentTimeMillis();
		for (int i = 0; i < addTime; i++) {
			executor.execute(() -> {
				addWithSema();
				latch2.countDown();
			});
		}
		try {
			latch2.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("time taked by semaphore: " + (System.currentTimeMillis() - startTime2));
		System.out.println(countWithSema);
		// reentrantLock
		Long startTime3 = System.currentTimeMillis();
		for (int i = 0; i < addTime; i++) {
			executor.execute(() -> {
				addWithLock();
				latch3.countDown();
			});
		}
		try {
			latch3.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("time taked by reentrantLock: " + (System.currentTimeMillis() - startTime3));
		System.out.println(countWithReen);
		// reentrantLock
		Long startTime4 = System.currentTimeMillis();
		for (int i = 0; i < addTime; i++) {
			executor.execute(() -> {
				addWithAtomic();
				latch4.countDown();
			});
		}
		try {
			latch4.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("time taked by AtomicInteger: " + (System.currentTimeMillis() - startTime4));
		System.out.println(countWithAtomic.get());

		executor.shutdown();
	}

	private static void addWithSyn() {
		synchronized (synLock) {
			countWithSyn++;
		}
	}

	private static void addWithLock() {
		reenLock.lock();
		countWithReen++;
		reenLock.unlock();
	}

	private static void addWithAtomic() {
		countWithAtomic.getAndIncrement();
	}

	private static void addWithSema() {
		try {
			semaLock.acquire(); // 产生一个信号
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		countWithSema++;
		semaLock.release(); // 解除一个信号
	}

	public static void main(String[] args) throws Exception {
		compareBetweenSemaphoreAndSyn();
	}

}
