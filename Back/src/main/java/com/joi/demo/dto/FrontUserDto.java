package com.joi.demo.dto;

public class FrontUserDto {
  private long id;
  private String userName;
  private String realName;
  private String idCard;
  private String aid;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  public String getAid() {
    return aid;
  }

  public void setAid(String aid) {
    this.aid = aid;
  }
}
