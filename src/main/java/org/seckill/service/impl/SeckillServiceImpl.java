package org.seckill.service.impl;

import java.util.Date;
import java.util.List;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,10);
    }

    @Override
    public Seckill getSeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        } else {
            Date start = seckill.getStartTime();
            Date end = seckill.getEndTime();
            long now = System.currentTimeMillis();
            if (now >= start.getTime() && now <= end.getTime()) {
                return new Exposer(true, seckillId, getMd5(seckillId));
            } else {
                return new Exposer(false, seckillId, now, start.getTime(), end.getTime());
            }
        }
    }

    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, String md5, long userPhone) throws SeckillException, SeckillCloseException, RepeatKillException {
        if (md5 == null || !getMd5(seckillId).equals(md5)) {
            throw new SeckillException("seckill data rewrite");
        }
        try {
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount == 0) {
                throw new RepeatKillException("seckill repeated");
            } else {
                int reduceNumber = seckillDao.reduceNumber(seckillId, new Date());
                if (reduceNumber == 0) {
                    throw new SeckillCloseException("seckill is close");
                } else {
                    SuccessKilled successKilled = successKilledDao.querySuccessKilledById(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException | RepeatKillException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SeckillException("seckill inner error:".concat(e.getMessage()));
        }
    }
}
