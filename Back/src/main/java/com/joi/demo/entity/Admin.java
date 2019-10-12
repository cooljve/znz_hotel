package com.joi.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "znz_admin")
public class Admin implements Serializable {

  private static final long serialVersionUID = 8933093516250815928L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "aid", nullable = false, unique = true)
  private String aid;

  @Column(name = "id_card", nullable = false)
  private String idCard;

  @Column(name = "email_address", nullable = false)
  private String emailAddress;

  @Column(name = "email_pwd")
  private String emailPwd;

  @Column(name = "real_name", nullable = false)
  private String realName;

  @Column(name = "password", nullable = false)
  private String password;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "hid",unique = true)
  @JsonManagedReference
  private Hotel hotel;

  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name = "aid")
  @JsonManagedReference
  private List<FrontUser> frontUsers;

  public Admin() {
  }

  public Admin(String idCard, String emailAddress, String realName, String password, Hotel hotel) {
    this.idCard = idCard;
    this.emailAddress = emailAddress;
    this.realName = realName;
    this.password = password;
    this.hotel = hotel;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAid() {
    return aid;
  }

  public void setAid(String aid) {
    this.aid = aid;
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

  public List<FrontUser> getFrontUsers() {
    return frontUsers;
  }

  public void setFrontUsers(List<FrontUser> frontUsers) {
    this.frontUsers = frontUsers;
  }
}
