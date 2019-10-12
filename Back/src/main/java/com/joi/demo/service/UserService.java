package com.joi.demo.service;

import com.joi.demo.dto.UserDto;
import com.joi.demo.dto.UserInsertPropDto;
import com.joi.demo.entity.User;
import com.joi.demo.vo.UserInfo;

public interface UserService {
  public boolean addUser(UserInsertPropDto userDto) throws Exception;

  public UserDto login(UserInfo user) throws Exception;

  public boolean modifyPwd(String userName,String oldPwd,String newPwd)throws Exception;
}
