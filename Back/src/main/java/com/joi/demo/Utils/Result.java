package com.joi.demo.Utils;

public class Result {
  private boolean isSuccess;
  private String message;
  private Object data;

  public Result(boolean isSuccess, String message, Object data) {
    this.isSuccess = isSuccess;
    this.message = message;
    this.data = data;
  }

  public boolean isSuccess() {
    return isSuccess;
  }

  public void setSuccess(boolean success) {
    isSuccess = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
