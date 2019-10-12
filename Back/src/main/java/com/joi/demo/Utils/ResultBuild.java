package com.joi.demo.Utils;

import com.joi.demo.Utils.Result;

public class ResultBuild {
  public static Result buildSuccessResult(Object data) {
    return buildResult(true,"成功",data);
  }

  public static Result buildFailResult(String message) {
    return buildResult(false, message, null);
  }

  private static Result buildResult(boolean flag,String msg, Object data) {
    return new Result(flag,msg,data);
  }
}
