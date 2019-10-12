package com.joi.demo.dto;

public class ChangePwdDto {
  private String aid;
  private String oldPwd;
  private String newPwd;

  public String getAid() {
    return aid;
  }

  public void setAid(String aid) {
    this.aid = aid;
  }

  public String getOldPwd() {
    return oldPwd;
  }

  public void setOldPwd(String oldPwd) {
    this.oldPwd = oldPwd;
  }

  public String getNewPwd() {
    return newPwd;
  }

  public void setNewPwd(String newPwd) {
    this.newPwd = newPwd;
  }
}
