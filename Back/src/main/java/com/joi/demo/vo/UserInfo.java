package com.joi.demo.vo;

import javax.validation.constraints.NotEmpty;

public class UserInfo {
  @NotEmpty(message = "用户名不能为空")
  private String username;
  @NotEmpty(message = "密码不能为空")
  private String password;

  private String type;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
