package org.seckill.service;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.forkJoin.SeckillTask;
import org.seckill.forkJoin.WorkThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillServiceTest {

    @Autowired
    private SeckillService service;

    @Test
    public void exportSeckillUrl() {
        Exposer exposer = service.exportSeckillUrl(1000);
        Assert.assertEquals(exposer.getMd5(), service.getMd5(1000));
    }

    @Test
    public void executeSeckill() {
        ExecutorService threadPool = Executors.newFixedThreadPool(200);
        for (int i = 0; i < 10000; i++) {
            // 模拟并发秒杀
            threadPool.execute(() -> {
                service.executeSeckill(1000, service.getMd5(1000), Long.valueOf("137".concat(String.format("%08d", new Random().nextInt(99999999)))));
            });
        }
        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                return;
            }
        }
    }

    @Test
    public void forkJoinSeckillTest() throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(200, new WorkThreadFactory(), null, false);
        SeckillTask task = new SeckillTask(service, 0, 10000);
        pool.execute(task);
        task.join();
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);
    }
}