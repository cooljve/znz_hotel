package com.joi.demo.service;

import com.joi.demo.dto.AdminDto;
import com.joi.demo.dto.UserInsertPropDto;
import com.joi.demo.entity.Admin;
import com.joi.demo.repository.AdminRepository;
import com.joi.demo.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.joi.demo.Utils.Constants.*;
import static com.joi.demo.Utils.Convert.convertToAdminDto;
import static com.joi.demo.Utils.PasswordUtils.verifyPwd;

@Service
public class AdminServiceImpl implements AdminService {

  @Autowired
  private AdminRepository adminRepository;

  @Transactional
  @Override
  public boolean addAdmin(UserInsertPropDto userDto) throws Exception {
    Admin admin = new Admin();
    if (adminRepository.findByAid(userDto.getUserName()) == null) {
      admin.setRealName(userDto.getRealName());
      admin.setIdCard(userDto.getIdCard());
      admin.setEmailAddress(userDto.getEmailAddress());
      admin.setPassword(userDto.getPassword());
      admin.setAid(userDto.getUserName());
      return adminRepository.save(admin);
    } else {
      throw new Exception(USER_NAME_EXIST);
    }
  }

  @Override
  public AdminDto login(UserInfo loginAdmin) throws Exception {
    Admin admin = adminRepository.findByAid(loginAdmin.getUsername());
    if (admin == null) {
      throw new Exception(USER_NOT_EXIST);
    }
    if (verifyPwd(loginAdmin.getPassword(), admin.getPassword())) {
      AdminDto adminDto = new AdminDto();
      convertToAdminDto(adminDto, admin);
      return adminDto;
    } else {
      throw new Exception(PWD_ERROR);
    }
  }

  @Override
  public boolean modifyPwd(String aid, String oldPwd, String newPwd) throws Exception {
    Admin admin = adminRepository.findByAid(aid);
    if (verifyPwd(oldPwd, admin.getPassword())) {
      admin.setPassword(newPwd);
      return adminRepository.update(admin);
    } else {
      throw new Exception(PWD_ERROR);
    }
  }

  @Override
  public AdminDto getAdminByAid(String aid) {
    Admin admin = new Admin();
    AdminDto adminDto = new AdminDto();
    try {
      admin = adminRepository.findByAid(aid);
    } catch (Exception e) {
      e.printStackTrace();
    }
    convertToAdminDto(adminDto, admin);
    return adminDto;
  }

  @Transactional
  @Override
  public boolean modify(AdminDto adminDto) {
    Admin admin = (Admin) adminRepository.findById(Admin.class, adminDto.getId());
    admin.setAid(adminDto.getAid());
    admin.setEmailAddress(adminDto.getEmailAddress());
    admin.setRealName(adminDto.getRealName());
    admin.setIdCard(adminDto.getIdCard());
    admin.setEmailPwd(adminDto.getEmailPwd());
    return adminRepository.update(admin);
  }


}
