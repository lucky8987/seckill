package org.seckill.entity;

import java.util.Date;

public class Seckill {

      private long seckillId;
      private String name;
      private long number;
      private Date startTime;
      private Date endTime;
      private Date createTime;

      public long getSeckillId() {
        return seckillId;
      }

      public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
      }


      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }


      public long getNumber() {
        return number;
      }

      public void setNumber(long number) {
        this.number = number;
      }


      public Date getStartTime() {
        return startTime;
      }

      public void setStartTime(java.sql.Timestamp startTime) {
        this.startTime = startTime;
      }


      public Date getEndTime() {
        return endTime;
      }

      public void setEndTime(java.sql.Timestamp endTime) {
        this.endTime = endTime;
      }


      public Date getCreateTime() {
        return createTime;
      }

      public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
      }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Seckill{");
        sb.append("seckillId=").append(seckillId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", number=").append(number);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
