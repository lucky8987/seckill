package org.seckill.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

/**
 * 工作线程-线程池
 */
public class WorkThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {


    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        return new WorkThread(pool);
    }
}
