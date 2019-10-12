package com.joi.demo.repository;

import com.joi.demo.entity.Staff;
import org.springframework.transaction.annotation.Transactional;

public interface StaffRepository extends BaseRepository  {
  Staff findBySid(long sid);

  @Transactional
  int delete(long rid);
}
