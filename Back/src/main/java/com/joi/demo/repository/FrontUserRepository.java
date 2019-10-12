package com.joi.demo.repository;

import com.joi.demo.entity.FrontUser;
import org.springframework.transaction.annotation.Transactional;

public interface FrontUserRepository extends BaseRepository {
  FrontUser findByUserName(String userName);

  @Transactional
  int delete(String userName);
}
