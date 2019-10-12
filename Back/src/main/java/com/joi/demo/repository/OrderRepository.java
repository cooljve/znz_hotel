package com.joi.demo.repository;

import com.joi.demo.entity.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository {
  List<Order> findAllOrders();
}
