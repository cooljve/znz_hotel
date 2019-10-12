package com.joi.demo.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "znz_cus")
public class Customer implements Serializable {

  private static final long serialVersionUID = -3966971678426378041L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cid;

  private String realName;

  private String idCard;

  private String emailAddress;

  public Customer() {
  }

  public Customer(String realName, String idCard, String emailAddress) {
    this.realName = realName;
    this.idCard = idCard;
    this.emailAddress = emailAddress;
  }

  public Long getCid() {
    return cid;
  }

  public void setCid(Long cid) {
    this.cid = cid;
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
}
