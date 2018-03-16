package org.seckill.entity;


import java.util.Date;

public class SuccessKilled {

  private long seckillId;
  private long userPhone;
  private long state;
  private Date createTime;


  public long getSeckillId() {
    return seckillId;
  }

  public void setSeckillId(long seckillId) {
    this.seckillId = seckillId;
  }


  public long getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(long userPhone) {
    this.userPhone = userPhone;
  }


  public long getState() {
    return state;
  }

  public void setState(long state) {
    this.state = state;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SuccessKilled{");
        sb.append("seckillId=").append(seckillId);
        sb.append(", userPhone=").append(userPhone);
        sb.append(", state=").append(state);
        sb.append(", createTime=").append(createTime);
        sb.append('}');
        return sb.toString();
    }
}
