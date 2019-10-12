package com.joi.demo.dto;

import java.util.List;

public class RoomsDto {
  private RoomCount roomCount;
  private List<RoomDto> roomDtoList;
  private List<RoomTypeDto> roomTypeDtos;

  public RoomsDto(RoomCount roomCount, List<RoomDto> roomDtoList, List<RoomTypeDto> roomTypeDtos) {
    this.roomCount = roomCount;
    this.roomDtoList = roomDtoList;
    this.roomTypeDtos = roomTypeDtos;
  }

  public RoomCount getRoomCount() {
    return roomCount;
  }

  public void setRoomCount(RoomCount roomCount) {
    this.roomCount = roomCount;
  }

  public List<RoomDto> getRoomDtoList() {
    return roomDtoList;
  }

  public void setRoomDtoList(List<RoomDto> roomDtoList) {
    this.roomDtoList = roomDtoList;
  }

  public List<RoomTypeDto> getRoomTypeDtos() {
    return roomTypeDtos;
  }

  public void setRoomTypeDtos(List<RoomTypeDto> roomTypeDtos) {
    this.roomTypeDtos = roomTypeDtos;
  }
}
