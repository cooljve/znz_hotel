package com.joi.demo.service;

import com.joi.demo.dto.OtherCostDto;

public interface OtherCostService {
  boolean addOtherCost(OtherCostDto otherCostDto);

  boolean modifyOtherCost(OtherCostDto otherCostDto);

  boolean deleteOtherCost(long ocid);
}
