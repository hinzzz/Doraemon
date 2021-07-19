package com.hinz.aop.utils;

import com.hinz.aop.function.TripleConsumer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yudong
 * @date 2021/6/10
 */
@Slf4j
public class ExecutorUtils {

    public static ExecutorService executorService;

    static {
        /*
         * corePoolSize 线程池常驻核心线程数
         * maximumPoolSize 线程池任务最多时，最大可以创建的线程数
         * keepAliveTime 线程的存活时间，让线程池空闲，且超过了该时间，多余的线程会销毁
         * unit 线程存活时间单位
         * workQueue 线程池执行的任务队列，线程池所有线程都在执行任务时，来了任务会放到队列
         * threadFactory 线程池创建工厂
         * handler 拒绝策略，workQueue满了，也不能创建新的线程，会用到此策略
         * 		AbortPolicy:终止策略，抛异常
         * 		CallerRunsPolicy:任务交给当前线程来执行
         * 		DiscardPolicy:忽略此任务，最新的任务
         * 		DiscardOldestPolicy:忽略最早的任务（最新加入队列的任务）
         *
         * 使用无界任务队列，线程池的任务队列可以无限制的添加新的任务，而线程池创建的最大线程数量就是corePoolSize设置
         * 的数量，也就是说在这种情况下maximumPoolSize这个参数是无效的，哪怕你的任务队列中缓存了很多未执行的任务，当线程
         * 池的线程数达到corePoolSize后，就不会再增加了；若后续有新的任务加入，则直接进入队列等待
         */
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2, 30,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new NamedThreadFactory());
    }

    static class NamedThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(Thread.currentThread().getThreadGroup(), r,
                    "pool-bluemoon-common-aop-" + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }

    /**
     * 启动异步任务
     *
     * @param t 入参t
     * @param u 入参u
     * @param v 入参v
     */
    public static <T, U, V> void startAsyncTask(T t, U u, V v, TripleConsumer<T, U, V> tripleConsumer) {
        executorService.submit(() -> {
            try {
                tripleConsumer.accept(t, u, v);
            } catch (Exception e) {
                log.error("async1 task fail t:{},u:{},v:{}", t, u, v, e);
            }
        });
    }

    /**
     * 启动异步任务,失败情况下会重试3次
     *
     * @param t 入参t
     * @param u 入参u
     * @param v 入参v
     */
    public static <T, U, V> void startRetryAsyncTask(T t, U u, V v, TripleConsumer<T, U, V> tripleConsumer) {
        executorService.submit(() -> {
            int tryTimes = 3;
            int i = 1;
            do {
                try {
                    tripleConsumer.accept(t, u, v);
                    break;
                } catch (Exception e) {
                    if (i < tryTimes) {
                        log.error("async task fail t:{},u:{},exec times:{},error msg:{}", t, u, i, e.getMessage());
                        try {
                            TimeUnit.SECONDS.sleep(3L * i);
                        } catch (InterruptedException ignored) {
                        }
                    } else {
                        log.error("async task fail t:{},u:{},v:{}", t, u, v, e);
                    }
                }
            } while (i++ < tryTimes);
        });
    }
}
