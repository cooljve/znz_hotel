package com.joi.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "znz_hotel")
public class Hotel implements Serializable {

  private static final long serialVersionUID = -3836299252310547649L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long hid;

  @OneToOne(mappedBy = "hotel")
  @JsonBackReference
  private Admin admin;

  private String district;

  private String location;

  private String hotelName;

  private String details;

  private double grade;

  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name = "hid")
  @JsonManagedReference
  private List<Photo> photos;

  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name = "hid")
  @JsonManagedReference
  private List<Room> rooms;

  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name = "hid")
  @JsonManagedReference
  private List<Staff> staff;

  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name = "hid")
  @JsonManagedReference
  private List<OtherCost> otherCosts;

  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JoinColumn(name = "hid")
  @JsonManagedReference
  private List<RoomType> roomTypes;

  public Hotel() {
  }


  public Long getHid() {
    return hid;
  }

  public void setHid(Long hid) {
    this.hid = hid;
  }

  public Admin getAdmin() {
    return admin;
  }

  public void setAdmin(Admin admin) {
    this.admin = admin;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getHotelName() {
    return hotelName;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public double getGrade() {
    return grade;
  }

  public void setGrade(double grade) {
    this.grade = grade;
  }

  public List<Photo> getPhotos() {
    return photos;
  }

  public void setPhotos(List<Photo> photos) {
    this.photos = photos;
  }

  public List<Room> getRooms() {
    return rooms;
  }

  public void setRooms(List<Room> rooms) {
    this.rooms = rooms;
  }

  public List<Staff> getStaff() {
    return staff;
  }

  public void setStaff(List<Staff> staff) {
    this.staff = staff;
  }

  public List<OtherCost> getOtherCosts() {
    return otherCosts;
  }

  public void setOtherCosts(List<OtherCost> otherCosts) {
    this.otherCosts = otherCosts;
  }

  public List<RoomType> getRoomTypes() {
    return roomTypes;
  }

  public void setRoomTypes(List<RoomType> roomTypes) {
    this.roomTypes = roomTypes;
  }
}
