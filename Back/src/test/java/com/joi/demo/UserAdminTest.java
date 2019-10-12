package com.joi.demo;

import com.joi.demo.entity.Admin;
import com.joi.demo.entity.User;
import com.joi.demo.service.AdminService;
import com.joi.demo.service.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UserAdminTest {

  @Autowired
  private UserService userService;
  @Autowired
  private AdminService adminService;

//  @Test
//  @Ignore
//  public void testModifyPwd() throws Exception {
//    adminService.modifyPwd(4L, "liujoi","joiliu");
//  }

  @Test
//  public void testAddUserAdmin()throws Exception {
//    User user = expectUser();
//    boolean addU = userService.addUser(user);
//    Admin admin = expectAdmin();
//    boolean addA = adminService.addAdmin(admin);
//
//    assertTrue(addU);
//    assertTrue(addA);
//  }

  private User expectUser() {
    User user = new User();
    user.setRealName("珏刘");
    user.setIdCard("432252452345234");
    user.setEmailAddress("fdafhdas@qq.com");
    user.setUserName("joiliu1");
    user.setPassword("liujoi");
    return user;
  }

  private Admin expectAdmin() {
    Admin admin = new Admin();
    admin.setAid("liujoi1");
    admin.setRealName("刘珏");
    admin.setIdCard("43214332484815");
    admin.setPassword("liujoi");
    admin.setEmailAddress("1232@qq.com");
    return admin;
  }
}
