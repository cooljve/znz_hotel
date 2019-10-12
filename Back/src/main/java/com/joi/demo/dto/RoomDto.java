package com.joi.demo.dto;

import java.util.List;

public class RoomDto {
  private long rid;
  private int floorNum;
  private int roomNumber;
  private String status;
  private String type;
  private double price;
  private List<String> configuration;
  private OrderDto order;

  public long getRid() {
    return rid;
  }

  public void setRid(long rid) {
    this.rid = rid;
  }

  public int getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
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

  public OrderDto getOrder() {
    return order;
  }

  public void setOrder(OrderDto order) {
    this.order = order;
  }
}
