package com.joi.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "znz_room_type")
public class RoomType implements Serializable {
  private static final long serialVersionUID = 5708375153167898525L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rtid;

  private String roomType;

  private int bedCount;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "hid")
  @JsonBackReference
  private Hotel hotel;

  public Long getRtid() {
    return rtid;
  }

  public void setRtid(Long rtid) {
    this.rtid = rtid;
  }

  public String getRoomType() {
    return roomType;
  }

  public void setRoomType(String roomType) {
    this.roomType = roomType;
  }

  public int getBedCount() {
    return bedCount;
  }

  public void setBedCount(int bedCount) {
    this.bedCount = bedCount;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }
}
