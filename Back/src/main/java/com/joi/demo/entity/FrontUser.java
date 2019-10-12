package com.joi.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "znz_front_user")
public class FrontUser implements Serializable {
  private static final long serialVersionUID = -5774276034631068010L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long fuid;

  @Column(name = "user_name", nullable = false, unique = true)
  private String userName;

  @Column(name = "id_card", nullable = false)
  private String idCard;

  @Column(name = "real_name", nullable = false)
  private String realName;

  @Column(name = "password", nullable = false)
  private String password;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "aid")
  @JsonBackReference
  private Admin admin;

  public Long getFuid() {
    return fuid;
  }

  public void setFuid(Long fuid) {
    this.fuid = fuid;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Admin getAdmin() {
    return admin;
  }

  public void setAdmin(Admin admin) {
    this.admin = admin;
  }
}
