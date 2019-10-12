package com.joi.demo.repository;

import com.joi.demo.entity.RoomType;
import org.springframework.transaction.annotation.Transactional;

public interface RoomTypeRepository extends BaseRepository {

  @Transactional
  int delete(long rtid);

  RoomType findByRtid(long rtid);
}
