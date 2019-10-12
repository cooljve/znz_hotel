package com.joi.demo.dto;

public class StaffDto {
  private long hid;
  private long sid;
  private int serialNum;
  private String work;
  private int count;
  private double salary;

  public long getHid() {
    return hid;
  }

  public void setHid(long hid) {
    this.hid = hid;
  }

  public long getSid() {
    return sid;
  }

  public void setSid(long sid) {
    this.sid = sid;
  }

  public int getSerialNum() {
    return serialNum;
  }

  public void setSerialNum(int serialNum) {
    this.serialNum = serialNum;
  }

  public String getWork() {
    return work;
  }

  public void setWork(String work) {
    this.work = work;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }
}
