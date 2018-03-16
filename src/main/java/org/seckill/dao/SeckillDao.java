package org.seckill.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.*;
import org.seckill.entity.Seckill;

/**
 * 秒杀库存Dao
 */
@Mapper
public interface SeckillDao {

    /**
     * 根据id查询秒杀对象
     *
     * @param seckillId
     * @return
     */
    @Select("select * from seckill where seckill_id = #{seckillId}")
    Seckill queryById(long seckillId);

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    @Update("update seckill set number = number - 1 where seckill_id = #{seckillId} and start_time <= #{killTime} and end_time >= #{killTime} and number > 0")
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime")Date killTime);

    /**
     * 查询秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    @Select("select * from seckill limit #{offset},#{limit}")
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

}
