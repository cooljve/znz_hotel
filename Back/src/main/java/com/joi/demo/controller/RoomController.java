package com.joi.demo.controller;

import com.joi.demo.Utils.Result;
import com.joi.demo.Utils.ResultBuild;
import com.joi.demo.dto.*;
import com.joi.demo.service.AdminService;
import com.joi.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.joi.demo.Utils.Constants.SERVER_ERROR;
import static com.joi.demo.Utils.DateUtils.getEveryDayByStartAndEndDate;

@RestController
@RequestMapping(value = "api/room")
public class RoomController {

  @Autowired
  private RoomService roomService;

  @Autowired
  private AdminService adminService;

  @CrossOrigin
  @RequestMapping(value = "addRoom")
  public Result insertRoom(@RequestBody RoomInsertPropDto roomInsertPropDto) {
    if (roomService.insertRoom(roomInsertPropDto)) {
      AdminDto adminDto = adminService.getAdminByAid(roomInsertPropDto.getAid());
      return ResultBuild.buildSuccessResult(adminDto);
    }
    return ResultBuild.buildFailResult(SERVER_ERROR);
  }

  @CrossOrigin
  @RequestMapping(value = "addRoomBatch")
  public Result insertRooms(@RequestBody RoomInsertPropDto roomInsertPropDto) {
    if (roomService.batchInsertRooms(roomInsertPropDto)) {
      AdminDto adminDto = adminService.getAdminByAid(roomInsertPropDto.getAid());
      return ResultBuild.buildSuccessResult(adminDto);
    }
    return ResultBuild.buildFailResult(SERVER_ERROR);
  }

  @CrossOrigin
  @RequestMapping(value = "getAllRooms")
  public Result getAllRooms(@RequestBody String aid) {
    RoomsDto roomsDto = roomService.getAll(aid);
    return ResultBuild.buildSuccessResult(roomsDto);
  }

  @CrossOrigin
  @RequestMapping(value = "getAllOrdersDate")
  public Result getAllOrdersDate(@RequestBody long rid) {
    List<OrderDto> orderDtos = roomService.getAllOrders(rid);
    List<Long> dateList = new ArrayList<>();
    for (OrderDto orderDto : orderDtos) {
      getEveryDayByStartAndEndDate(dateList,orderDto.getDate());
    }
    return ResultBuild.buildSuccessResult(dateList);
  }

  @CrossOrigin
  @RequestMapping(value = "modifyRoom")
  public Result modifyRoom(@RequestBody RoomDto roomDto) {
    boolean flag = false;
    try {
      flag = roomService.modifyRoom(roomDto);
    } catch (Exception e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
    if (flag) {
      return ResultBuild.buildSuccessResult(true);
    }
    return ResultBuild.buildFailResult(SERVER_ERROR);
  }

  @CrossOrigin
  @RequestMapping(value = "deleteRoom")
  public Result deleteRoom(@RequestBody long rid) {
    boolean flag = false;
    try {
      flag = roomService.deleteRoom(rid);
    } catch (Exception e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
    if (flag) {
      return ResultBuild.buildSuccessResult(true);
    }
    return ResultBuild.buildFailResult(SERVER_ERROR);
  }

  @CrossOrigin
  @RequestMapping(value = "select")
  public Result searchRoom(@RequestBody RoomSearchCriteriaDto searchCriteria) {
    List<RoomDto> roomDtos= roomService.searchRoom(searchCriteria);
    return ResultBuild.buildSuccessResult(roomDtos);
  }

  @CrossOrigin
  @RequestMapping(value = "book")
  public Result bookRoom(@RequestBody BookRoomDto bookRoomDto) {
    int roomNumber= roomService.bookRoom(bookRoomDto);
    return ResultBuild.buildSuccessResult(roomNumber);
  }

  @CrossOrigin
  @RequestMapping(value = "clean")
  public Result cleanRoom(@RequestBody long rid) {
    boolean flag;
    flag = roomService.cleanRoom(rid);
    if (flag) {
      return ResultBuild.buildSuccessResult(true);
    }
    return ResultBuild.buildFailResult(SERVER_ERROR);
  }
}
