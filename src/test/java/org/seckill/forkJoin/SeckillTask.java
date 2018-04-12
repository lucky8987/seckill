package org.seckill.forkJoin;

import java.util.Random;
import java.util.concurrent.RecursiveAction;
import org.seckill.service.SeckillService;

public class SeckillTask extends RecursiveAction {

    public static final int threadhold = 5;

    private SeckillService service;

    private int start;

    private int end;

    public SeckillTask(SeckillService service, int start, int end) {
        this.service = service;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if ((end - start) <= threadhold) {
            for (int i = start; i < end; i++) {
                WorkThread myWorkThread = (WorkThread) Thread.currentThread();
                myWorkThread.addTask();
                service.executeSeckill(1000, service.getMd5(1000), Long.valueOf("137".concat(String.format("%08d", new Random().nextInt(99999999)))));
            }
        } else {
            int middle = (end + start)/2;
            SeckillTask task1 = new SeckillTask(service, start, middle);
            SeckillTask task2 = new SeckillTask(service, middle, end);
            task1.fork();
            task2.fork();
        }
    }

}
