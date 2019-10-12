package com.joi.demo.repository;

import com.joi.demo.dto.BookRoomDto;
import com.joi.demo.dto.RoomSearchCriteriaDto;
import com.joi.demo.entity.Hotel;
import com.joi.demo.entity.Order;
import com.joi.demo.entity.Room;

import java.util.List;

public interface RoomRepository extends BaseRepository {
  int getMaxFloorNum(Hotel hotel, int floorNum);

  List<Room> getAllRooms(Hotel hotel);

  List<Object[]> getRoomCount(Hotel hotel);

  int delete(long rid);

  Room findByRoomNumberAndHotel(int roomNumber, Hotel hotel);

  List<Room> searchRoom(RoomSearchCriteriaDto searchCriteria);

  Room findFitRoom(BookRoomDto bookRoomDto);

  List<Order> getAllOrders(long rid);
}
