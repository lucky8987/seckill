package org.seckill.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.seckill.entity.SuccessKilled;

@Mapper
public interface SuccessKilledDao {

    @Insert("insert ignore into success_killed (seckill_id, user_phone, state) values (#{seckillId}, #{userPhone}, 0)")
    int insertSuccessKilled(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);

    @Select("select * from success_killed where seckill_id = #{seckillId} and user_phone = #{userPhone} limit 1")
    SuccessKilled querySuccessKilledById(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);
}
