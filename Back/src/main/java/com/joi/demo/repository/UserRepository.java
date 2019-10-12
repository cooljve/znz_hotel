package com.joi.demo.repository;

import com.joi.demo.entity.User;

public interface UserRepository extends BaseRepository {

  public String findPasswordById(long uid);

  public User findByUserName(String userName) throws Exception;
}
