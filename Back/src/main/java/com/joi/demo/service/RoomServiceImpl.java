package com.joi.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joi.demo.dto.*;
import com.joi.demo.entity.Admin;
import com.joi.demo.entity.Hotel;
import com.joi.demo.entity.Order;
import com.joi.demo.entity.Room;
import com.joi.demo.repository.AdminRepository;
import com.joi.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.joi.demo.Utils.Constants.*;
import static com.joi.demo.Utils.Convert.*;
import static com.joi.demo.Utils.DateUtils.computeDays;
import static com.joi.demo.Utils.EntityUtils.buildRoomTypeDtos;

@Service
public class RoomServiceImpl implements RoomService {

  private ObjectMapper objMapper = new ObjectMapper();

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private RoomRepository roomRepository;

  @Override
  public boolean batchInsertRooms(RoomInsertPropDto roomInsertDto) {
    List<Room> roomList = getRooms(roomInsertDto);
    return roomRepository.batchInsert(roomList);
  }

  @Override
  public boolean insertRoom(RoomInsertPropDto roomInsertDto) {
    Admin admin = getAdmin(roomInsertDto.getAid());
    Room room = configRoom(admin.getHotel(), roomInsertDto);
    room.setRoomNumber(roomRepository.getMaxFloorNum(admin.getHotel(), roomInsertDto.getFloorNum()) + 1);
    return roomRepository.save(room);
  }

  @Override
  public RoomsDto getAll(String aid) {
    Admin admin = getAdmin(aid);
    List<Room> roomList = roomRepository.getAllRooms(admin.getHotel());
    List<RoomDto> roomDtos = buildRoomDtos(roomList);
    List<RoomTypeDto> roomTypeDtos = buildRoomTypeDtos(admin.getHotel());
    RoomCount roomCount = new RoomCount();
    List<Object[]> countList = roomRepository.getRoomCount(admin.getHotel());
    handleRoomCount(roomCount, countList);
    roomCount.setHid(admin.getHotel().getHid());
    return new RoomsDto(roomCount, roomDtos, roomTypeDtos);
  }

  @Override
  public boolean modifyRoom(RoomDto roomDto) throws Exception {
    boolean flag;
    Room room = (Room) roomRepository.findById(Room.class, roomDto.getRid());
    if (room == null) {
      throw new Exception(ROOM_NOT_EXIST);
    }
    convertToRoom(room, roomDto);
    if (room.getRoomNumber() != roomDto.getRoomNumber()) {
      setNewRoomNum(roomDto, room);
    }
    flag = roomRepository.update(room);
    return flag;

  }

  private void setNewRoomNum(RoomDto roomDto, Room room) throws Exception {
    Room r = roomRepository.findByRoomNumberAndHotel(roomDto.getRoomNumber(), room.getHotel());
    if (r == null) {
      room.setRoomNumber(roomDto.getRoomNumber());
      room.setFloorNum(roomDto.getRoomNumber() / 100);
    } else {
      throw new Exception(ROOM_NUMBER_EXIST);
    }
  }

  @Override
  public boolean deleteRoom(long rid) {
    int count = roomRepository.delete(rid);
    return count == 1;
  }

  @Override
  public List<RoomDto> searchRoom(RoomSearchCriteriaDto searchCriteria) {
    List<RoomDto> roomDtos = new ArrayList<>();
    convertToRoomDto(roomDtos, roomRepository.searchRoom(searchCriteria));
    return roomDtos;
  }

  @Override
  public boolean cleanRoom(long rid) {
    Room room = (Room) roomRepository.findById(Room.class, rid);
    if (room.getRoomStatus().equals("ED")) {
      room.setRoomStatus("EC");
    }
    if (room.getRoomStatus().equals("D")) {
      room.setRoomStatus("C");
    }
    return roomRepository.update(room);
  }

  @Override
  public int bookRoom(BookRoomDto bookRoomDto) {
    Room room = roomRepository.findFitRoom(bookRoomDto);
    if (room == null) {
      return 0;
    }
    Order order = new Order();
    convertToOrder(order, bookRoomDto.getOrder());
    double earning = room.getRoomPrice() * computeDays(bookRoomDto.getOrder().getDate()[0], bookRoomDto.getOrder().getDate()[1]);
    order.setEarnings(earning);
    order.setCreateDate(getChinaTime());
    order.setStatus(ORDER_STATUS_BOOK);
    order.setFrontUserName(bookRoomDto.getOrder().getFrontUserName());
    room.getOrders().add(order);
    if (roomRepository.update(room)) {
      return room.getRoomNumber();
    }
    return 0;
  }

  @Override
  public List<OrderDto> getAllOrders(long rid) {
    List<Order> orderList = roomRepository.getAllOrders(rid);
    List<OrderDto> orderDtos = new ArrayList<>();
    convertToOrderDto(orderDtos, orderList);
    return orderDtos;
  }

  private void handleRoomCount(RoomCount roomCount, List<Object[]> countList) {
    for (Object[] objects : countList) {
      if (objects[0].equals("EC")) {
        roomCount.setEmptyClean((long) objects[1]);
        continue;
      }
      if (objects[0].equals("ED")) {
        roomCount.setEmptyDirty((long) objects[1]);
        continue;
      }
      if (objects[0].equals("C")) {
        roomCount.setClean((long) objects[1]);
        continue;
      }
      if (objects[0].equals("D")) {
        roomCount.setDirty((long) objects[1]);
      }
    }
    roomCount.setTotal(roomCount.getClean() + roomCount.getDirty() +
        roomCount.getEmptyClean() + roomCount.getEmptyDirty());
  }

  private List<RoomDto> buildRoomDtos(List<Room> roomList) {
    List<RoomDto> roomDtos = new ArrayList<>();
    for (Room room : roomList) {
      RoomDto roomDto = new RoomDto();
      convertToRoomDto(roomDto, room);
      roomDtos.add(roomDto);
    }
    return roomDtos;
  }

  private Admin getAdmin(String aid) {
    Admin admin = new Admin();
    try {
      admin = adminRepository.findByAid(aid);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return admin;
  }

  private List<Room> getRooms(RoomInsertPropDto roomInsertDto) {
    List<Room> roomList = new ArrayList<>();
    Admin admin = getAdmin(roomInsertDto.getAid());
    int maxNum = roomRepository.getMaxFloorNum(admin.getHotel(), roomInsertDto.getFloorNum()) + 1;
    for (int n = 0; n < roomInsertDto.getCount(); n++) {
      Room room = configRoom(admin.getHotel(), roomInsertDto);
      room.setRoomNumber(maxNum++);
      roomList.add(room);
    }
    return roomList;
  }

  private Room configRoom(Hotel hotel, RoomInsertPropDto roomInsertDto) {
    Room room = new Room();
    room.setHotel(hotel);
    room.setRoomType(roomInsertDto.getType());
    room.setRoomStatus(roomInsertDto.getStatus());
    room.setFloorNum(roomInsertDto.getFloorNum());
    String conf = "";
    try {
      conf = objMapper.writeValueAsString(roomInsertDto.getConfiguration());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    room.setConfiguration(conf);
    room.setRoomPrice(roomInsertDto.getPrice());
    return room;
  }
}
