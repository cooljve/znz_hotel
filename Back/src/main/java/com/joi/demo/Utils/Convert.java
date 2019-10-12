package com.joi.demo.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joi.demo.dto.*;
import com.joi.demo.entity.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.joi.demo.Utils.Constants.*;
import static com.joi.demo.Utils.EmailUtils.getRandomString;

public class Convert {

  private static ObjectMapper objMapper = new ObjectMapper();

  public static void convertToUserDto(UserDto userDto, User user) {
    userDto.setId(user.getId());
    userDto.setIdCard(user.getIdCard());
    userDto.setEmailAddress(user.getEmailAddress());
    userDto.setRealName(user.getRealName());
    userDto.setUserName(user.getUserName());
    List<OrderDto> orderDtos = new ArrayList<>();
    convertToOrderDto(orderDtos, user.getOrders());
    userDto.setOrderDtoList(orderDtos);
  }

  public static void convertToFrontUserDto(FrontUserDto userDto, FrontUser user) {
    userDto.setId(user.getFuid());
    userDto.setIdCard(user.getIdCard());
    userDto.setRealName(user.getRealName());
    userDto.setUserName(user.getUserName());
    userDto.setAid(user.getAdmin().getAid());
  }

  public static void convertToFrontUser(FrontUser user,FrontUserDto userDto) {
    user.setRealName(userDto.getRealName());
    user.setIdCard(userDto.getIdCard());
  }

  public static void convertToAdminDto(AdminDto adminDto, Admin admin) {
    adminDto.setId(admin.getId());
    adminDto.setAid(admin.getAid());
    adminDto.setRealName(admin.getRealName());
    adminDto.setIdCard(admin.getIdCard());
    adminDto.setEmailAddress(admin.getEmailAddress());
    adminDto.setEmailPwd(admin.getEmailPwd());
    adminDto.setHaveHotel(!(admin.getHotel() == null));
    if (admin.getHotel() != null) {
      adminDto.setHaveHotel(true);
    } else {
      adminDto.setHaveHotel(false);
    }
  }

  public static void convertToHotelDto(HotelDto hotelDto, Hotel hotel) {
    List<String> city = new ArrayList<>();
    try {
      city = objMapper.readValue(hotel.getDistrict(), List.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    hotelDto.setPhotos(hotel.getPhotos());
    hotelDto.setAid(hotel.getAdmin().getAid());
    hotelDto.setHotelName(hotel.getHotelName());
    hotelDto.setDetails(hotel.getDetails());
    hotelDto.setLocation(hotel.getLocation());
    hotelDto.setCity(city);
    hotelDto.setHid(hotel.getHid());
    hotelDto.setGrade(hotel.getGrade());
  }

  public static void convertToHotel(Hotel hotel, HotelDto hotelDto) {
    String district = "";
    try {
      district = objMapper.writeValueAsString(hotelDto.getCity());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    hotel.setDistrict(district);
    hotel.setDetails(hotelDto.getDetails());
    hotel.setLocation(hotelDto.getLocation());
    hotel.setHotelName(hotelDto.getHotelName());
  }

  public static void convertToRoom(Room room, RoomDto roomDto) {
    String configs = "";
    try {
      configs = objMapper.writeValueAsString(roomDto.getConfiguration());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    room.setConfiguration(configs);
    room.setRoomPrice(roomDto.getPrice());
    room.setRoomStatus(roomDto.getStatus());
    room.setRoomType(roomDto.getType());
  }

  public static void convertToRoomDto(List<RoomDto> roomDtoList, List<Room> roomList) {
    for (Room room : roomList) {
      RoomDto roomDto = new RoomDto();
      convertToRoomDto(roomDto, room);
      roomDtoList.add(roomDto);
    }
  }

  public static void convertToRoomDto(RoomDto roomDto, Room room) {
    List<String> configs = new ArrayList<>();
    try {
      configs = objMapper.readValue(room.getConfiguration(), ArrayList.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    roomDto.setConfiguration(configs);
    roomDto.setRid(room.getRid());
    roomDto.setRoomNumber(room.getRoomNumber());
    roomDto.setFloorNum(room.getFloorNum());
    roomDto.setPrice(room.getRoomPrice());
    if (room.getRoomStatus().equals("ED")) {
      roomDto.setStatus(ED);
    } else if (room.getRoomStatus().equals("EC")) {
      roomDto.setStatus(EC);
    } else if (room.getRoomStatus().equals("C")) {
      roomDto.setStatus(C);
    } else {
      roomDto.setStatus(D);
    }
    roomDto.setType(room.getRoomType());
    if (room.getOrders().size() != 0) {
      roomDto.setOrder(getCurrOrderDto(room.getOrders()));
    } else {
      roomDto.setOrder(new OrderDto());
    }
  }

  public static OrderDto getCurrOrderDto(List<Order> orders) {
    OrderDto orderDto = new OrderDto();
    Date now = getChinaTime();
    for (Order order : orders) {
      if (order.getStartDate().compareTo(now) <= 0 && order.getEndDate().compareTo(now) >= 0) {
        convertToOrderDto(orderDto, order);
        break;
      }
    }
    return orderDto;
  }

  public static void convertToOrderDto(List<OrderDto> orderDtos, List<Order> orderList) {
    for (Order order : orderList) {
      OrderDto orderDto = new OrderDto();
      convertToOrderDto(orderDto, order);
      orderDtos.add(orderDto);
    }
  }

  public static void convertToOrderDto(OrderDto orderDto, Order order) {
    orderDto.setDate(new Date[]{order.getStartDate(), order.getEndDate()});
    orderDto.setEmailAddress(order.getCustomer().getEmailAddress());
    orderDto.setIdCard(order.getCustomer().getIdCard());
    orderDto.setRealName(order.getCustomer().getRealName());
    orderDto.setInform(order.isInform());
    orderDto.setOid(order.getOid());
    orderDto.setRoomNumber(order.getRoom().getRoomNumber());
    orderDto.setRate(order.getGrade());
    orderDto.setCost(order.getEarnings());
    orderDto.setStatus(order.getStatus());
    orderDto.setFrontUserName(order.getFrontUserName());
    //如果结束时间在当前时间之前，则状态为离店
//    if (getChinaTime().compareTo(order.getEndDate()) >= 0) {
//      orderDto.setStatus(ORDER_STATUS_CHECKOUT);
//    } else if (getChinaTime().compareTo(order.getStartDate())<0) {//
//      //当前时间在开始时间之前，则状态为预订
//      orderDto.setStatus(ORDER_STATUS_BOOK);
//    }else {
//      orderDto.setStatus(ORDER_STATUS_CHECKIN);
//    }
  }

  public static void convertToOrder(Order order, OrderDto orderDto) {
    order.setStartDate(orderDto.getDate()[0]);
    order.setEndDate(orderDto.getDate()[1]);
    order.setCustomer(new Customer(orderDto.getRealName(), orderDto.getIdCard(), orderDto.getEmailAddress()));
    order.setInform(orderDto.isInform());
    order.setDoorPassword(getRandomString());
    order.setGrade(orderDto.getRate());
    order.setStatus(orderDto.getStatus());
  }

  public static void convertToStaffDto(StaffDto staffDto, Staff staff) {
    staffDto.setWork(staff.getWork());
    staffDto.setCount(staff.getCount());
    staffDto.setSalary(staff.getSalary());
    staffDto.setHid(staff.getHotel().getHid());
    staffDto.setSid(staff.getSid());
  }

  public static void convertToStaff(Staff staff, StaffDto staffDto) {
    staff.setWork(staffDto.getWork());
    staff.setCount(staffDto.getCount());
    staff.setSalary(staffDto.getSalary());
    staff.setTotal(staffDto.getCount() * staffDto.getSalary());
  }

  public static void convertToRoomTypeDto(RoomTypeDto roomTypeDto, RoomType roomType) {
    roomTypeDto.setRoomType(roomType.getRoomType());
    roomTypeDto.setBedCount(roomType.getBedCount());
    roomTypeDto.setHid(roomType.getHotel().getHid());
    roomTypeDto.setRtid(roomType.getRtid());
  }

  public static void convertToRoomType(RoomType roomType, RoomTypeDto roomTypeDto) {
    roomType.setRoomType(roomTypeDto.getRoomType());
    roomType.setBedCount(roomTypeDto.getBedCount());
  }

  public static void convertToOtherCost(OtherCost otherCost, OtherCostDto otherCostDto) {
    otherCost.setDescription(otherCostDto.getDesc());
    otherCost.setDate(otherCostDto.getDate());
    otherCost.setCost(otherCostDto.getCost());
    otherCost.setOut(otherCostDto.getOut().equals(TABLE_OUT));
  }

  public static void convertToOtherCostDto(OtherCostDto otherCostDto, OtherCost otherCost) {
    otherCostDto.setCost(otherCost.getCost());
    otherCostDto.setDesc(otherCost.getDescription());
    otherCostDto.setDate(otherCost.getDate());
    otherCostDto.setHid(otherCost.getHotel().getHid());
    otherCostDto.setOcid(otherCost.getOcid());
    if (otherCost.isOut()) {
      otherCostDto.setOut(TABLE_OUT);
    }else otherCostDto.setOut(TABLE_IN);
  }


  public static Date getChinaTime() {
    TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date now = new Date();
    sdf.setTimeZone(timeZone);
    String str = sdf.format(now);
    try {
      return sdf.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return now;
  }

  public static String formatDate(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
    sdf.setTimeZone(timeZone);
    String str = sdf.format(date);
    return str;
  }
}
