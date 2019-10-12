package com.joi.demo.controller;


import com.joi.demo.Utils.Result;
import com.joi.demo.Utils.ResultBuild;
import com.joi.demo.dto.OrderDto;
import com.joi.demo.dto.ReportDateRangeDto;
import com.joi.demo.dto.RoomDto;
import com.joi.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.joi.demo.Utils.Constants.SERVER_ERROR;

@RestController
@RequestMapping(value = "api/order")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @CrossOrigin
  @RequestMapping(value = "getAllOrders")
  public Result getAllOrders(@RequestBody String aid) {
    List<OrderDto> orderDtos;
    orderDtos = orderService.getAllOrders(aid);
    return ResultBuild.buildSuccessResult(orderDtos);
  }

  @CrossOrigin
  @RequestMapping(value = "getOrdersByDate")
  public Result getOrdersByDate(@RequestBody ReportDateRangeDto dateRangeDto) {
    List<OrderDto> orderDtos;
    orderDtos = orderService.getOrdersByDate(dateRangeDto);
    return ResultBuild.buildSuccessResult(orderDtos);
  }

  @CrossOrigin
  @RequestMapping(value = "checkIn")
  public Result checkIn(@RequestBody RoomDto roomDto) {
    boolean flag=orderService.checkIn(roomDto);
    if (flag) {
      return ResultBuild.buildSuccessResult(true);
    }
    return ResultBuild.buildFailResult(SERVER_ERROR);
  }

  @CrossOrigin
  @RequestMapping(value = "modify")
  public Result modify(@RequestBody RoomDto roomDto) {
    boolean flag=orderService.modify(roomDto);
    if (flag) {
      return ResultBuild.buildSuccessResult(true);
    }
    return ResultBuild.buildFailResult(SERVER_ERROR);
  }

  @CrossOrigin
  @RequestMapping(value = "checkOut")
  public Result checkOut(@RequestBody RoomDto roomDto) {
    double res=orderService.checkOut(roomDto);
    return ResultBuild.buildSuccessResult(res);
  }

  @CrossOrigin
  @RequestMapping(value = "sendEmailToCus")
  public Result sendEmailToCus(@RequestBody OrderDto orderDto) {
    boolean flag;
    try {
      flag = orderService.sendEmailToCus(orderDto);
    } catch (Exception e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
    return ResultBuild.buildSuccessResult(flag);
  }

}
