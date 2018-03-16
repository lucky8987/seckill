package org.seckill.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao dao;

    @Test
    @Rollback
    public void insertSuccessKilled() {
        int result = dao.insertSuccessKilled(1000, Long.valueOf("13020287221"));
        Assert.assertEquals(result, 1);
    }

    @Test
    public void querySuccessKilledById() {
        SuccessKilled successKilled = dao.querySuccessKilledById(1000, 13020287221L);
        Assert.assertEquals(successKilled.getSeckillId(), 1000);
    }
}