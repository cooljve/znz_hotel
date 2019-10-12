package com.joi.demo.service;

import com.joi.demo.dto.HotelDto;
import com.joi.demo.dto.ReportDateRangeDto;
import com.joi.demo.dto.ReportDto;
import com.joi.demo.dto.ReportSetDto;
import com.joi.demo.entity.Admin;
import com.joi.demo.entity.Hotel;

import java.io.InputStream;

public interface HotelService {
  public Hotel findHotel(Long id);

  public boolean addHotel(HotelDto hotelDto)throws Exception;

  public boolean modifyHotel(HotelDto hotelDto)throws Exception;

  HotelDto getHotelByAid(String aid) throws Exception;

  ReportSetDto getReportSetByAid(String aid) throws Exception;

  ReportDto getReport(ReportDateRangeDto rangeDto);

  void uploadPic(InputStream is, String aid);
}
