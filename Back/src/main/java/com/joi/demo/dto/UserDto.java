package com.joi.demo.dto;

import java.util.List;

public class UserDto {
  private long id;
  private String userName;
  private String realName;
  private String idCard;
  private String emailAddress;
  private List<OrderDto> orderDtoList;

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

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public List<OrderDto> getOrderDtoList() {
    return orderDtoList;
  }

  public void setOrderDtoList(List<OrderDto> orderDtoList) {
    this.orderDtoList = orderDtoList;
  }
}
