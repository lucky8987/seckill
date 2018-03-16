package org.seckill.service;

import java.util.List;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

public interface SeckillService {

    Logger log = LoggerFactory.getLogger(SeckillService.class);

    String salt = "sdfqweionnlkdfg-9-90./l;ls;qwewqe";

    /**
     * 获取秒杀商品列表
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 获取单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getSeckillById(long seckillId);

    /**
     * 秒杀开启是输出秒杀地址，否则输出当前时间
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     * @param seckillId
     * @param md5
     * @param userPhone
     * @return
     * @throws SeckillException
     * @throws SeckillCloseException
     * @throws RepeatKillException
     */
    SeckillExecution executeSeckill(long seckillId, String md5, long userPhone) throws SeckillException, SeckillCloseException, RepeatKillException;

    default String getMd5(long seckillId) {
        String meta = seckillId + "/" + salt;
        return DigestUtils.md5DigestAsHex(meta.getBytes());
    }
}
