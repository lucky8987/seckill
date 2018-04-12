package org.seckill.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

/**
 * 工作线程
 */
public class WorkThread extends ForkJoinWorkerThread {


    // 计数器， 记录每个线程的工作量
    private static ThreadLocal<Integer> taskCounter = new ThreadLocal<>();


    /**
     * Creates a ForkJoinWorkerThread operating in the given pool.
     *
     * @param pool the pool this thread works in
     *
     * @throws NullPointerException if pool is null
     */
    protected WorkThread(ForkJoinPool pool) {
        super(pool);
    }

    /**
     * 线程初始化
     */
    @Override
    protected void onStart() {
        super.onStart();
        System.out.printf("WorkerThread %d: Initializing task counter.\n",getId());
        taskCounter.set(0);
    }

    /**
     * 线程销毁
     * @param exception
     */
    @Override
    protected void onTermination(Throwable exception) {
        // 输出当前线程的工作量
        System.out.printf("WorkerThread %d: %d\n",getId(),taskCounter.get());
        super.onTermination(exception);
    }

    /**
     * 执行任务时，累加计数器
     */
    public void addTask() {
        taskCounter.set(taskCounter.get().intValue()+1);
    }
}
