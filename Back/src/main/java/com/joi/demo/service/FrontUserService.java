package com.joi.demo.service;

import com.joi.demo.dto.ChangePwdDto;
import com.joi.demo.dto.FrontUserDto;
import com.joi.demo.dto.UserDto;
import com.joi.demo.dto.UserInsertPropDto;
import com.joi.demo.vo.UserInfo;

import java.util.List;

public interface FrontUserService {
  FrontUserDto login(UserInfo userInfo) throws Exception;

  boolean modifyPwd(String aid, String oldPwd, String newPwd) throws Exception;

  boolean addFrontStaff(UserInsertPropDto userInsertPropDto) throws Exception;

  boolean deleteFrontUser(String userName);

  boolean modifyFrontStaff(FrontUserDto frontUserDto);

  List<FrontUserDto> getFrontUsersByAid(String aid);

  boolean resetFrontUserPwd(ChangePwdDto changePwdDto);
}
