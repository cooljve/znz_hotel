package com.joi.demo.dto;

import com.joi.demo.entity.Photo;

import java.util.List;

public class HotelDto {
  private String aid;
  private long hid;
  private String hotelName;
  private List<String> city;
  private String location;
  private String details;
  private List<Photo> photos;
  private double grade;

  public String getAid() {
    return aid;
  }

  public void setAid(String aid) {
    this.aid = aid;
  }

  public long getHid() {
    return hid;
  }

  public void setHid(long hid) {
    this.hid = hid;
  }

  public String getHotelName() {
    return hotelName;
  }

  public void setHotelName(String hotelName) {
    this.hotelName = hotelName;
  }

  public List<String> getCity() {
    return city;
  }

  public void setCity(List<String> city) {
    this.city = city;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public List<Photo> getPhotos() {
    return photos;
  }

  public void setPhotos(List<Photo> photos) {
    this.photos = photos;
  }

  public double getGrade() {
    return grade;
  }

  public void setGrade(double grade) {
    this.grade = grade;
  }
}
