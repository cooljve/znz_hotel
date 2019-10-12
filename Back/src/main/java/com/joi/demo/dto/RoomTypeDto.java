package com.joi.demo.dto;

public class RoomTypeDto {

  private long hid;

  private long rtid;

  private int serialNum;

  private String roomType;

  private int bedCount;

  public long getHid() {
    return hid;
  }

  public void setHid(long hid) {
    this.hid = hid;
  }

  public long getRtid() {
    return rtid;
  }

  public void setRtid(long rtid) {
    this.rtid = rtid;
  }

  public int getSerialNum() {
    return serialNum;
  }

  public void setSerialNum(int serialNum) {
    this.serialNum = serialNum;
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
}
