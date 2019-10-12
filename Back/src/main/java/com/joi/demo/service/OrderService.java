package com.joi.demo.service;

import com.joi.demo.dto.OrderDto;
import com.joi.demo.dto.ReportDateRangeDto;
import com.joi.demo.dto.RoomDto;

import java.util.List;

public interface OrderService {
  boolean checkIn(RoomDto roomDto);

  double checkOut(RoomDto roomDto);

  boolean modify(RoomDto roomDto);

  List<OrderDto> getAllOrders(String aid);

  boolean sendEmailToCus(OrderDto orderDto) throws Exception;

  List<OrderDto> getOrdersByDate(ReportDateRangeDto dateRangeDto);
}
