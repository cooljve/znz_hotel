package com.joi.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

@Entity
@Table(name = "znz_photo")
public class Photo implements Serializable {

  private static final long serialVersionUID = 213177678456545882L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pid;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "hid")
  @JsonBackReference
  private Hotel hotel;

  @Lob
  private byte[] picture;

  public Long getPid() {
    return pid;
  }

  public void setPid(Long pid) {
    this.pid = pid;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  public void setPicture(byte[] picture) {
    this.picture = picture;
  }

  public byte[] getPicture() {
    return picture;
  }
}
