package com.joi.demo.dto;

public class BookRoomDto {
  private String type;
  private OrderDto order;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public OrderDto getOrder() {
    return order;
  }

  public void setOrder(OrderDto order) {
    this.order = order;
  }
}
