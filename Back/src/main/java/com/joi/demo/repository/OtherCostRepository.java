package com.joi.demo.repository;

import com.joi.demo.entity.OtherCost;

public interface OtherCostRepository extends BaseRepository {
  OtherCost findByOcid(long ocid);

  int delete(long ocid);
}
