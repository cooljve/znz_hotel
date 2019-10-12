package com.joi.demo.service;

import com.joi.demo.dto.StaffDto;

public interface StaffService {
  boolean addStaff(StaffDto staffDto);

  boolean modifyStaff(StaffDto staffDto);

  boolean deleteStaff(long sid);
}
