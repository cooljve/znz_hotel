package com.joi.demo.controller;

import com.joi.demo.Utils.Result;
import com.joi.demo.Utils.ResultBuild;
import com.joi.demo.dto.StaffDto;
import com.joi.demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/staff")
public class StaffController {

  @Autowired
  private StaffService staffService;

  @CrossOrigin
  @RequestMapping(value = "add")
  public Result addStaff(@RequestBody StaffDto staffDto) {
    boolean flag;
    flag = staffService.addStaff(staffDto);
    return ResultBuild.buildSuccessResult(flag);
  }

  @CrossOrigin
  @RequestMapping(value = "modify")
  public Result modifyStaff(@RequestBody StaffDto staffDto) {
    boolean flag;
    flag = staffService.modifyStaff(staffDto);
    return ResultBuild.buildSuccessResult(flag);
  }

  @CrossOrigin
  @RequestMapping(value = "delete")
  public Result deleteStaff(@RequestBody long sid) {
    boolean flag;
    flag = staffService.deleteStaff(sid);
    return ResultBuild.buildSuccessResult(flag);
  }

}
