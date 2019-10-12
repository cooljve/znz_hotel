package com.joi.demo.controller;

import com.joi.demo.Utils.Result;
import com.joi.demo.Utils.ResultBuild;
import com.joi.demo.dto.RoomTypeDto;
import com.joi.demo.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/roomType")
public class RoomTypeController {

  @Autowired
  private RoomTypeService roomTypeService;

  @CrossOrigin
  @RequestMapping(value = "add")
  public Result addRoomType(@RequestBody RoomTypeDto roomTypeDto) {
    boolean flag;
    flag = roomTypeService.addRoomType(roomTypeDto);
    return ResultBuild.buildSuccessResult(flag);
  }

  @CrossOrigin
  @RequestMapping(value = "modify")
  public Result modifyRoomType(@RequestBody RoomTypeDto roomTypeDto) {
    boolean flag;
    flag = roomTypeService.modifyRoomType(roomTypeDto);
    return ResultBuild.buildSuccessResult(flag);
  }

  @CrossOrigin
  @RequestMapping(value = "delete")
  public Result deleteRoomType(@RequestBody long rtid) {
    boolean flag;
    flag = roomTypeService.deleteRoomType(rtid);
    return ResultBuild.buildSuccessResult(flag);
  }

}
