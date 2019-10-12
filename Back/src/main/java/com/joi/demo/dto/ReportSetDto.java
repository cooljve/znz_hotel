package com.joi.demo.dto;

import java.util.List;

public class ReportSetDto {
  private long hid;

  private List<OtherCostDto> otherCostDtos;

  private List<StaffDto> staffDtos;

  private List<RoomTypeDto> roomTypeDtos;

  public long getHid() {
    return hid;
  }

  public void setHid(long hid) {
    this.hid = hid;
  }

  public List<OtherCostDto> getOtherCostDtos() {
    return otherCostDtos;
  }

  public void setOtherCostDtos(List<OtherCostDto> otherCostDtos) {
    this.otherCostDtos = otherCostDtos;
  }

  public List<StaffDto> getStaffDtos() {
    return staffDtos;
  }

  public void setStaffDtos(List<StaffDto> staffDtos) {
    this.staffDtos = staffDtos;
  }

  public List<RoomTypeDto> getRoomTypeDtos() {
    return roomTypeDtos;
  }

  public void setRoomTypeDtos(List<RoomTypeDto> roomTypeDtos) {
    this.roomTypeDtos = roomTypeDtos;
  }
}
