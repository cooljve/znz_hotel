package com.joi.demo.dto;

public class AdminDto {
  private long id;
  private String aid;
  private String realName;
  private String idCard;
  private String emailAddress;
  private String emailPwd;
  private boolean haveHotel;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getAid() {
    return aid;
  }

  public void setAid(String aid) {
    this.aid = aid;
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

  public String getEmailPwd() {
    return emailPwd;
  }

  public void setEmailPwd(String emailPwd) {
    this.emailPwd = emailPwd;
  }

  public boolean isHaveHotel() {
    return haveHotel;
  }

  public void setHaveHotel(boolean haveHotel) {
    this.haveHotel = haveHotel;
  }
}
