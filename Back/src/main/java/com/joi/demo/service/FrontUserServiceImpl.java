package com.joi.demo.service;

import com.joi.demo.dto.*;
import com.joi.demo.entity.Admin;
import com.joi.demo.entity.FrontUser;
import com.joi.demo.repository.AdminRepository;
import com.joi.demo.repository.FrontUserRepository;
import com.joi.demo.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.joi.demo.Utils.Constants.PWD_ERROR;
import static com.joi.demo.Utils.Constants.USER_NAME_EXIST;
import static com.joi.demo.Utils.Constants.USER_NOT_EXIST;
import static com.joi.demo.Utils.Convert.convertToAdminDto;
import static com.joi.demo.Utils.Convert.convertToFrontUser;
import static com.joi.demo.Utils.Convert.convertToFrontUserDto;
import static com.joi.demo.Utils.PasswordUtils.verifyPwd;

@Service
public class FrontUserServiceImpl implements FrontUserService {

  @Autowired
  private FrontUserRepository frontUserRepository;

  @Autowired
  private AdminRepository adminRepository;

  @Override
  public FrontUserDto login(UserInfo userInfo) throws Exception{
    FrontUser user = frontUserRepository.findByUserName(userInfo.getUsername());
    if (user == null) {
      throw new Exception(USER_NOT_EXIST);
    }
    if (verifyPwd(userInfo.getPassword(), user.getPassword())) {
      FrontUserDto userDto = new FrontUserDto();
      convertToFrontUserDto(userDto, user);
      return userDto;
    } else {
      throw new Exception(PWD_ERROR);
    }

  }

  @Override
  public boolean modifyPwd(String userName, String oldPwd, String newPwd) throws Exception{
    FrontUser frontUser = frontUserRepository.findByUserName(userName);
    if (verifyPwd(oldPwd, frontUser.getPassword())) {
      frontUser.setPassword(newPwd);
      return frontUserRepository.update(frontUser);
    } else {
      throw new Exception(PWD_ERROR);
    }
  }

  @Override
  public boolean addFrontStaff(UserInsertPropDto userDto) throws Exception {
    FrontUser user = new FrontUser();
    Admin admin = adminRepository.findByAid(userDto.getAid());
    if(frontUserRepository.findByUserName(userDto.getUserName())==null){
      user.setAdmin(admin);
      user.setIdCard(userDto.getIdCard());
      user.setRealName(userDto.getRealName());
      user.setPassword(userDto.getPassword());
      user.setUserName(userDto.getUserName());
      return frontUserRepository.save(user);
    }else {
      throw new Exception(USER_NAME_EXIST);
    }
  }

  @Override
  public boolean deleteFrontUser(String userName) {
    int count=frontUserRepository.delete(userName);
    return count == 1;
  }

  @Override
  public boolean modifyFrontStaff(FrontUserDto userDto) {
    FrontUser frontUser = frontUserRepository.findByUserName(userDto.getUserName());
    convertToFrontUser(frontUser, userDto);
    return frontUserRepository.update(frontUser);
  }

  @Override
  public List<FrontUserDto> getFrontUsersByAid(String aid) {
    List<FrontUserDto> frontUserDtos = new ArrayList<>();
    Admin admin = adminRepository.findByAid(aid);
    for (FrontUser frontUser : admin.getFrontUsers()) {
      FrontUserDto userDto = new FrontUserDto();
      convertToFrontUserDto(userDto,frontUser);
      frontUserDtos.add(userDto);
    }
    return frontUserDtos;
  }

  @Override
  public boolean resetFrontUserPwd(ChangePwdDto changePwdDto) {
    FrontUser frontUser = frontUserRepository.findByUserName(changePwdDto.getAid());
    frontUser.setPassword(changePwdDto.getNewPwd());
    return frontUserRepository.update(frontUser);
  }
}
