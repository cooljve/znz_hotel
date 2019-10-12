package com.joi.demo.service;

import com.joi.demo.dto.StaffDto;
import com.joi.demo.entity.Hotel;
import com.joi.demo.entity.Staff;
import com.joi.demo.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joi.demo.Utils.Convert.convertToStaff;

@Service
public class StaffServiceImpl implements StaffService {

  @Autowired
  private StaffRepository staffRepository;

  @Override
  public boolean addStaff(StaffDto staffDto) {
    Hotel hotel = (Hotel) staffRepository.findById(Hotel.class, staffDto.getHid());
    Staff staff = new Staff();
    staff.setHotel(hotel);
    convertToStaff(staff, staffDto);
    return staffRepository.save(staff);
  }

  @Override
  public boolean modifyStaff(StaffDto staffDto) {
    Staff staff = staffRepository.findBySid(staffDto.getSid());
    convertToStaff(staff, staffDto);
    return staffRepository.update(staff);
  }

  @Override
  public boolean deleteStaff(long sid) {
    int count = staffRepository.delete(sid);
    return count == 1;
  }
}
