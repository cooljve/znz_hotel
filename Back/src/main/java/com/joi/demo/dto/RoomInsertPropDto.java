package com.joi.demo.dto;

import java.util.List;

public class RoomInsertPropDto {
  private String aid;
  private int floorNum;
  private String status;
  private String type;
  private double price;
  private List<String> configuration;
  private int count;

  public String getAid() {
    return aid;
  }

  public void setAid(String aid) {
    this.aid = aid;
  }

  public int getFloorNum() {
    return floorNum;
  }

  public void setFloorNum(int floorNum) {
    this.floorNum = floorNum;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public List<String> getConfiguration() {
    return configuration;
  }

  public void setConfiguration(List<String> configuration) {
    this.configuration = configuration;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
