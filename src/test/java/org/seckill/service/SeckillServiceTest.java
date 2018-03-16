package org.seckill.service;

import com.sun.deploy.util.SyncFileAccess;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.exception.SeckillCloseException;
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

    @Test(expected = SeckillCloseException.class)
    public void executeSeckill() {
        ThreadGroup threadGroup = new ThreadGroup("executeSeckill");
        for (int i = 0; i < 10; i++) {
            // 模拟并发秒杀
            new Thread(threadGroup,() -> {
                service.executeSeckill(1000, service.getMd5(1000), Long.valueOf("137".concat(String.format("%18d", new Random().nextInt(200)))));
            }).start();
        }
        while (threadGroup.activeCount() > 1);
    }
}