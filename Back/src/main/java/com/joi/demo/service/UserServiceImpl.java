package com.joi.demo.service;

import com.joi.demo.dto.UserDto;
import com.joi.demo.dto.UserInsertPropDto;
import com.joi.demo.entity.User;
import com.joi.demo.repository.UserRepository;
import com.joi.demo.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joi.demo.Utils.Constants.PWD_ERROR;
import static com.joi.demo.Utils.Constants.USER_NAME_EXIST;
import static com.joi.demo.Utils.Convert.convertToUserDto;
import static com.joi.demo.Utils.PasswordUtils.verifyPwd;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  public UserRepository userRepository;

  @Override
  public boolean addUser(UserInsertPropDto userDto) throws Exception {
    User user = new User();
    if (userRepository.findByUserName(userDto.getUserName()) == null) {
      user.setUserName(userDto.getUserName());
      user.setIdCard(userDto.getIdCard());
      user.setEmailAddress(userDto.getEmailAddress());
      user.setRealName(userDto.getRealName());
      user.setPassword(userDto.getPassword());
      return userRepository.save(user);
    }else {
      throw new Exception(USER_NAME_EXIST);
    }
  }

  @Override
  public UserDto login(UserInfo loginUser) throws Exception {
    if (loginUser.getUsername().isEmpty() || loginUser.getPassword().isEmpty()) {
      return null;
    }
    User user = userRepository.findByUserName(loginUser.getUsername());
    if (verifyPwd(loginUser.getPassword(), user.getPassword())) {
      UserDto userDto = new UserDto();
      convertToUserDto(userDto, user);
      return userDto;
    } else {
      throw new Exception(PWD_ERROR);
    }
  }

  @Override
  public boolean modifyPwd(String userName, String oldPwd, String newPwd) throws Exception {
    User user = userRepository.findByUserName(userName);
    if (verifyPwd(oldPwd, user.getPassword())) {
      user.setPassword(newPwd);
      return userRepository.update(user);
    } else {
      throw new Exception(PWD_ERROR);
    }
  }
}
