package com.joi.demo.Utils;

import com.joi.demo.dto.OtherCostDto;
import com.joi.demo.dto.RoomTypeDto;
import com.joi.demo.dto.StaffDto;
import com.joi.demo.entity.Hotel;
import com.joi.demo.entity.OtherCost;
import com.joi.demo.entity.RoomType;
import com.joi.demo.entity.Staff;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.joi.demo.Utils.Convert.convertToOtherCostDto;
import static com.joi.demo.Utils.Convert.convertToRoomTypeDto;
import static com.joi.demo.Utils.Convert.convertToStaffDto;

public class EntityUtils {

  public static List<RoomTypeDto> buildRoomTypeDtos(Hotel hotel) {
    List<RoomTypeDto> roomTypeDtos = new ArrayList<>();
    List<RoomType> roomTypeList = hotel.getRoomTypes();
    if (roomTypeList.size() != 0) {
      int i=1;
      for (RoomType roomType : roomTypeList) {
        RoomTypeDto roomTypeDto = new RoomTypeDto();
        convertToRoomTypeDto(roomTypeDto, roomType);
        roomTypeDto.setSerialNum(i++);
        roomTypeDtos.add(roomTypeDto);
      }
    }
    return roomTypeDtos;
  }

  public static List<StaffDto> buildStaffDtos(Hotel hotel) {
    List<StaffDto> staffDtos = new ArrayList<>();
    List<Staff> staffList = hotel.getStaff();
    if (staffList.size() != 0) {
      int i=1;
      for (Staff staff : staffList) {
        StaffDto staffDto = new StaffDto();
        convertToStaffDto(staffDto, staff);
        staffDto.setSerialNum(i++);
        staffDtos.add(staffDto);
      }
    }
    return staffDtos;
  }

  public static List<OtherCostDto> buildOtherCostDtos(Hotel hotel) {
    List<OtherCostDto> otherCostDtos = new ArrayList<>();
    List<OtherCost> otherCostList = hotel.getOtherCosts();
    if (otherCostList.size()!=0) {
      int i=1;
      for (OtherCost otherCost : otherCostList) {
        OtherCostDto otherCostDto = new OtherCostDto();
        convertToOtherCostDto(otherCostDto, otherCost);
        otherCostDto.setSerialNum(i++);
        otherCostDtos.add(otherCostDto);
      }
    }
    return otherCostDtos;
  }

}
