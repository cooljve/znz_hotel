package com.joi.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "znz_order")
public class Order implements Serializable {
  private static final long serialVersionUID = -5096410147042935956L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long oid;

  private Date startDate;

  private Date endDate;

  private Date createDate;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "rid")
  @JsonBackReference
  private Room room;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "cid",unique = true)
  private Customer customer;

  private String doorPassword;

  private double earnings;

  private boolean isInform;

  private double grade;

  private String status;

  private String frontUserName;

  public Long getOid() {
    return oid;
  }

  public void setOid(Long oid) {
    this.oid = oid;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public String getDoorPassword() {
    return doorPassword;
  }

  public void setDoorPassword(String doorPassword) {
    this.doorPassword = doorPassword;
  }

  public double getEarnings() {
    return earnings;
  }

  public void setEarnings(double earnings) {
    this.earnings = earnings;
  }

  public boolean isInform() {
    return isInform;
  }

  public void setInform(boolean inform) {
    isInform = inform;
  }

  public double getGrade() {
    return grade;
  }

  public void setGrade(double grade) {
    this.grade = grade;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getFrontUserName() {
    return frontUserName;
  }

  public void setFrontUserName(String frontUserName) {
    this.frontUserName = frontUserName;
  }
}
