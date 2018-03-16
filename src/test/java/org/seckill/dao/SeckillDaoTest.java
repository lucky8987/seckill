package org.seckill.dao;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillDaoTest {

    @Autowired
    private SeckillDao dao;

    @Test
    public void queryById() {
        Seckill seckill = dao.queryById(1000);
        Assert.assertEquals(seckill.getSeckillId(), 1000);
    }

    @Test
    public void queryAll() {
        List<Seckill> seckills = dao.queryAll(1, 10);
        Assert.assertNotNull(seckills);
    }
}