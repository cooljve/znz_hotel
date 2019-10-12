package com.joi.demo.Utils;

public class PasswordUtils {
  public static boolean verifyPwd(String inputPwd,String encrypedPwd) {
    return inputPwd.equals(encrypedPwd) ? true : false;
  }
}
