package com.joi.demo.service;

import com.joi.demo.dto.*;
import com.joi.demo.entity.*;
import com.joi.demo.repository.AdminRepository;
import com.joi.demo.repository.HotelRepository;
import com.joi.demo.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.joi.demo.Utils.Constants.INCOME;
import static com.joi.demo.Utils.Convert.convertToHotel;
import static com.joi.demo.Utils.Convert.convertToHotelDto;
import static com.joi.demo.Utils.DateUtils.computeDays;
import static com.joi.demo.Utils.DateUtils.inSelectedDate;
import static com.joi.demo.Utils.EntityUtils.*;

@Service
public class HotelServiceImpl implements HotelService {

  @Autowired
  private HotelRepository hotelRepository;
  @Autowired
  private AdminRepository adminRepository;
  @Autowired
  private PhotoRepository photoRepository;


  @Override
  public Hotel findHotel(Long id) {
    Hotel hotel;
    hotel = (Hotel) hotelRepository.findById(Hotel.class, id);
    return hotel;
  }

  @Override
  public boolean addHotel(HotelDto hotelDto) throws Exception {
    Hotel hotel = new Hotel();
    convertToHotel(hotel, hotelDto);
    Admin admin = adminRepository.findByAid(hotelDto.getAid());
    admin.setHotel(hotel);
    hotelRepository.save(hotel);
    boolean flag = adminRepository.update(admin);
    return flag;
  }

  @Override
  public boolean modifyHotel(HotelDto hotelDto) throws Exception {
    Admin admin = adminRepository.findByAid(hotelDto.getAid());
    Hotel hotel = admin.getHotel();
    convertToHotel(hotel, hotelDto);
    admin.setHotel(hotel);
    return adminRepository.update(admin);
  }

  @Override
  public HotelDto getHotelByAid(String aid) throws Exception {
    Admin admin = adminRepository.findByAid(aid);
    Hotel hotel;
    hotel = admin.getHotel();
    HotelDto hotelDto = new HotelDto();
    if (hotel != null) {
      convertToHotelDto(hotelDto, hotel);
      return hotelDto;
    }
    return null;
  }

  @Override
  public ReportSetDto getReportSetByAid(String aid) throws Exception {
    Admin admin = adminRepository.findByAid(aid);
    ReportSetDto reportSetDto = new ReportSetDto();
    reportSetDto.setHid(admin.getHotel().getHid());
    reportSetDto.setStaffDtos(buildStaffDtos(admin.getHotel()));
    reportSetDto.setRoomTypeDtos(buildRoomTypeDtos(admin.getHotel()));
    reportSetDto.setOtherCostDtos(buildOtherCostDtos(admin.getHotel()));
    return reportSetDto;
  }

  @Transactional
  @Override
  public ReportDto getReport(ReportDateRangeDto rangeDto) {
    Admin admin = adminRepository.findByAid(rangeDto.getAid());
    Hotel hotel = admin.getHotel();
    List<ReportTableDto> expend = computeExpend(hotel, rangeDto);
    List<ReportTableDto> income = computeIncome(hotel, rangeDto);
    return new ReportDto(expend, income);
  }

  @Override
  public void uploadPic(InputStream is, String aid) {
    Admin admin = adminRepository.findByAid(aid);
    Hotel hotel = admin.getHotel();
    Photo photo = new Photo();
    byte[] pic = new byte[0];
    try {
      pic = FileCopyUtils.copyToByteArray(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
    photo.setHotel(hotel);
    photo.setPicture(pic);
    photoRepository.save(photo);
  }

  private List<ReportTableDto> computeIncome(Hotel hotel, ReportDateRangeDto rangeDto) {
    List<ReportTableDto> tableDtos = new ArrayList<>();
    List<Room> rooms = hotel.getRooms();
    int i = 1;
    double num = 0;
    ReportTableDto tableDto = new ReportTableDto();
    for (Room room : rooms) {
      for (Order order : room.getOrders()) {
        if (inSelectedDate(order.getCreateDate(),rangeDto)) {
          tableDto.setSerialNum(i);
          tableDto.setName(INCOME);
          num += order.getEarnings();
        }
      }
    }
    if (num != 0) {
      tableDto.setMoney(num);
      tableDtos.add(tableDto);
    }
    List<OtherCost> otherCostList = hotel.getOtherCosts();
    for (OtherCost otherCost : otherCostList) {
      if (inSelectedDate(otherCost.getDate(), rangeDto)&& !otherCost.isOut()) {
        i = getCostFromOtherCost(tableDtos, i, otherCost);
      }
    }
    return tableDtos;
  }

  private int getCostFromOtherCost(List<ReportTableDto> tableDtos, int i, OtherCost otherCost) {
    ReportTableDto tableDto = new ReportTableDto();
    tableDto.setSerialNum(i++);
    tableDto.setName(otherCost.getDescription());
    tableDto.setMoney(otherCost.getCost());
    tableDtos.add(tableDto);
    return i;
  }

  private List<ReportTableDto> computeExpend(Hotel hotel, ReportDateRangeDto rangeDto) {
    List<ReportTableDto> tableDtos = new ArrayList<>();
    List<OtherCost> otherCostList = hotel.getOtherCosts();
    int i = 1;
    for (OtherCost otherCost : otherCostList) {
      if (inSelectedDate(otherCost.getDate(), rangeDto)&& otherCost.isOut()) {
        i = getCostFromOtherCost(tableDtos, i, otherCost);
      }
    }
    for (Staff staff : hotel.getStaff()) {
      ReportTableDto tableDto = new ReportTableDto();
      tableDto.setSerialNum(i++);
      tableDto.setName(staff.getWork());
      int days=computeDays(rangeDto.getStartDate(),rangeDto.getEndDate());
      days = days == 0 ? 1 : days;
      double money=Math.round(staff.getTotal()/30)*days;
      tableDto.setMoney(money);
      tableDtos.add(tableDto);
    }
    return tableDtos;
  }

}
