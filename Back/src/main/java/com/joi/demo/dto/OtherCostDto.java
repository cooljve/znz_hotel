package com.joi.demo.dto;

import java.util.Date;

public class OtherCostDto {
  private long hid;
  private long ocid;
  private int serialNum;
  private String desc;
  private double cost;
  private Date date;
  private String out;

  public long getHid() {
    return hid;
  }

  public void setHid(long hid) {
    this.hid = hid;
  }

  public long getOcid() {
    return ocid;
  }

  public void setOcid(long ocid) {
    this.ocid = ocid;
  }

  public int getSerialNum() {
    return serialNum;
  }

  public void setSerialNum(int serialNum) {
    this.serialNum = serialNum;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getOut() {
    return out;
  }

  public void setOut(String out) {
    this.out = out;
  }
}
