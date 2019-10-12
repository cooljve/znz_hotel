package com.joi.demo.service;

import com.joi.demo.dto.AdminDto;
import com.joi.demo.dto.FrontUserDto;
import com.joi.demo.dto.UserInsertPropDto;
import com.joi.demo.dto.ChangePwdDto;
import com.joi.demo.vo.UserInfo;

import java.util.List;

public interface AdminService {
  public boolean addAdmin(UserInsertPropDto userDto) throws Exception;

  public AdminDto login(UserInfo loginAdmin) throws Exception;

  public boolean modifyPwd(String aid,String oldPwd,String newPwd)throws Exception;

  public AdminDto getAdminByAid(String aid);

  boolean modify(AdminDto adminDto);

}
