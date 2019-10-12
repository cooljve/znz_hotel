package com.joi.demo.service;

import com.joi.demo.dto.*;

import java.util.List;

public interface RoomService {
  public boolean batchInsertRooms(RoomInsertPropDto roomInsertPropDto);
  public boolean insertRoom(RoomInsertPropDto roomInsertPropDto);

  RoomsDto getAll(String aid);

  boolean modifyRoom(RoomDto roomDto) throws Exception;

  boolean deleteRoom(long rid);

  List<RoomDto> searchRoom(RoomSearchCriteriaDto searchCriteria);

  boolean cleanRoom(long rid);

  int bookRoom(BookRoomDto bookRoomDto);

  List<OrderDto> getAllOrders(long rid);
}
