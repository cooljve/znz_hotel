package com.joi.demo.dto;

import java.util.Date;

public class OrderDto {

  private long oid;

  private int serialNum;

  private int roomNumber;

  private String realName;

  private String idCard;

  private String emailAddress;

  private String status;

  private Date[] date;

  private boolean inform;

  private double rate;

  private double cost;

  private String frontUserName;

  public long getOid() {
    return oid;
  }

  public void setOid(long oid) {
    this.oid = oid;
  }

  public int getSerialNum() {
    return serialNum;
  }

  public void setSerialNum(int serialNum) {
    this.serialNum = serialNum;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date[] getDate() {
    return date;
  }

  public void setDate(Date[] date) {
    this.date = date;
  }

  public boolean isInform() {
    return inform;
  }

  public void setInform(boolean inform) {
    this.inform = inform;
  }

  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public String getFrontUserName() {
    return frontUserName;
  }

  public void setFrontUserName(String frontUserName) {
    this.frontUserName = frontUserName;
  }
}
