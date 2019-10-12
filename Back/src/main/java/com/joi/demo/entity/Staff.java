package com.joi.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "znz_staff")
public class Staff implements Serializable {

  private static final long serialVersionUID = 3737392038814122432L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long sid;

  private String work;

  private int count;

  private double salary;

  private double total;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "hid")
  @JsonBackReference
  private Hotel hotel;

  public Long getSid() {
    return sid;
  }

  public void setSid(Long sid) {
    this.sid = sid;
  }

  public String getWork() {
    return work;
  }

  public void setWork(String work) {
    this.work = work;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public double getSalary() {
    return salary;
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }
}
