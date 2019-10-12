package com.joi.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joi.demo.Utils.Result;
import com.joi.demo.Utils.ResultBuild;
import com.joi.demo.dto.AdminDto;
import com.joi.demo.dto.FrontUserDto;
import com.joi.demo.dto.UserInsertPropDto;
import com.joi.demo.service.AdminService;
import com.joi.demo.service.FrontUserService;
import com.joi.demo.vo.UserInfo;
import com.joi.demo.dto.ChangePwdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.joi.demo.Utils.Constants.USER_ADMIN;
import static com.joi.demo.Utils.Constants.USER_FRONT;

@RestController
@RequestMapping(value = "api/user")
public class UserController {

  @Autowired
  private FrontUserService userService;
  @Autowired
  private AdminService adminService;

  private ObjectMapper objMapper = new ObjectMapper();

  @CrossOrigin
  @RequestMapping(value = "login")
  public Result login(@RequestBody UserInfo userInfo) {
    if (userInfo.getType().equals(USER_FRONT)) {
      try {
        FrontUserDto user = userService.login(userInfo);
        return ResultBuild.buildSuccessResult(user);
      } catch (Exception e) {
        return ResultBuild.buildFailResult(e.getMessage());
      }
    } else if (userInfo.getType().equals(USER_ADMIN)) {
      try {
        AdminDto adminDto = adminService.login(userInfo);
        return ResultBuild.buildSuccessResult(adminDto);
      } catch (Exception e) {
        return ResultBuild.buildFailResult(e.getMessage());
      }
    }
    return null;
  }

  @CrossOrigin
  @RequestMapping(value = "adminChangePwd")
  public Result changePassword(@RequestBody(required = false) String requestJson) throws Exception {
    boolean changed = false;
    ChangePwdDto changePwdDto = objMapper.readValue(requestJson, ChangePwdDto.class);
    try {
      changed = adminService.modifyPwd(changePwdDto.getAid(), changePwdDto.getOldPwd(), changePwdDto.getNewPwd());
      return ResultBuild.buildSuccessResult(changed);
    } catch (Exception e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
  }

  @CrossOrigin
  @RequestMapping(value = "modifyAdmin")
  public Result modifyAdmin(@RequestBody AdminDto adminDto) {
    boolean flag;
    flag = adminService.modify(adminDto);
    return ResultBuild.buildSuccessResult(flag);
  }

  @CrossOrigin
  @RequestMapping(value = "getAdminByAid")
  public Result getAdminByAid(@RequestBody String aid) {
    AdminDto adminDto;
    adminDto = adminService.getAdminByAid(aid);
    return ResultBuild.buildSuccessResult(adminDto);
  }

  @CrossOrigin
  @RequestMapping(value = "register")
  public Result register(@RequestBody(required = false) UserInsertPropDto userInsertPropDto) {
    boolean added = false;
    try {
      added = adminService.addAdmin(userInsertPropDto);
    } catch (Exception e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
    if (added) {
      return ResultBuild.buildSuccessResult(true);
    }
    return ResultBuild.buildSuccessResult(false);
  }

  @CrossOrigin
  @RequestMapping(value = "getFrontUsersByAid")
  public Result getFrontUsersByAid(@RequestBody String aid) {
    List<FrontUserDto> frontUsers;
    frontUsers = userService.getFrontUsersByAid(aid);
    return ResultBuild.buildSuccessResult(frontUsers);
  }

  @CrossOrigin
  @RequestMapping(value = "frontUserChangePwd")
  public Result frontUserChangePwd(@RequestBody ChangePwdDto changePwdDto) {
    boolean changed = false;
    try {
      changed = userService.modifyPwd(changePwdDto.getAid(), changePwdDto.getOldPwd(), changePwdDto.getNewPwd());
      return ResultBuild.buildSuccessResult(changed);
    } catch (Exception e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
  }

  @CrossOrigin
  @RequestMapping(value = "addFrontStaff")
  public Result addFrontStaff(@RequestBody(required = false) UserInsertPropDto userInsertPropDto) {
    boolean added = false;
    try {
      added = userService.addFrontStaff(userInsertPropDto);
    } catch (Exception e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
    if (added) {
      return ResultBuild.buildSuccessResult(true);
    }
    return ResultBuild.buildSuccessResult(false);
  }

  @CrossOrigin
  @RequestMapping(value = "modifyFrontStaff")
  public Result modifyFrontStaff(@RequestBody(required = false) FrontUserDto frontUserDto) {
    boolean added;
    try {
      added = userService.modifyFrontStaff(frontUserDto);
    } catch (Exception e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
    if (added) {
      return ResultBuild.buildSuccessResult(true);
    }
    return ResultBuild.buildSuccessResult(false);
  }

  @CrossOrigin
  @RequestMapping(value = "resetFrontUserPwd")
  public Result resetFrontUserPwd(@RequestBody ChangePwdDto changePwdDto) {
    boolean added;
    try {
      added = userService.resetFrontUserPwd(changePwdDto);
    } catch (Exception e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
    return ResultBuild.buildSuccessResult(added);
  }

  @CrossOrigin
  @RequestMapping(value = "deleteFrontUser")
  public Result deleteFrontUser(@RequestBody String userName) {
    boolean added;
    try {
      added = userService.deleteFrontUser(userName);
    } catch (Exception e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
    return ResultBuild.buildSuccessResult(added);
  }
}
