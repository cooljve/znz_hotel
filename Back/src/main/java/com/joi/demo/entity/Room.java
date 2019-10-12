package com.joi.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "znz_room")
public class Room implements Serializable {
  private static final long serialVersionUID = -385841218403525548L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rid;

  private String roomType;

  private double roomPrice;

  private Integer floorNum;

  @Column(nullable = false)
  private Integer roomNumber;

  private String configuration;

  private String roomStatus;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "hid")
  @JsonBackReference
  private Hotel hotel;

  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name = "rid")
  @JsonManagedReference
  private List<Order> orders;

  public Long getRid() {
    return rid;
  }

  public void setRid(Long rid) {
    this.rid = rid;
  }

  public String getRoomType() {
    return roomType;
  }

  public void setRoomType(String roomType) {
    this.roomType = roomType;
  }

  public double getRoomPrice() {
    return roomPrice;
  }

  public void setRoomPrice(double roomPrice) {
    this.roomPrice = roomPrice;
  }

  public Integer getFloorNum() {
    return floorNum;
  }

  public void setFloorNum(Integer floorNum) {
    this.floorNum = floorNum;
  }

  public Integer getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(Integer roomNumber) {
    this.roomNumber = roomNumber;
  }

  public String getConfiguration() {
    return configuration;
  }

  public void setConfiguration(String configuration) {
    this.configuration = configuration;
  }

  public String getRoomStatus() {
    return roomStatus;
  }

  public void setRoomStatus(String roomStatus) {
    this.roomStatus = roomStatus;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }
}
