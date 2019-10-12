package com.joi.demo.service;

import com.joi.demo.dto.RoomTypeDto;

public interface RoomTypeService {
  boolean addRoomType(RoomTypeDto roomTypeDto);

  boolean modifyRoomType(RoomTypeDto roomTypeDto);

  boolean deleteRoomType(long rtid);
}
