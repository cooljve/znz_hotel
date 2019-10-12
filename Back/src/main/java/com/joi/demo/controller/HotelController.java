package com.joi.demo.controller;

import com.joi.demo.Utils.Result;
import com.joi.demo.Utils.ResultBuild;
import com.joi.demo.dto.*;
import com.joi.demo.entity.Admin;
import com.joi.demo.entity.Photo;
import com.joi.demo.repository.PhotoRepository;
import com.joi.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.joi.demo.Utils.Constants.FILE_IS_EMPTY_UPLOAD_FAILED;
import static com.joi.demo.Utils.Constants.REPORT_DON_NOT_SELECT_DATE;

@RestController
@RequestMapping(value = "api/hotel")
public class HotelController {

  @Autowired
  private HotelService hotelService;

  @Autowired
  private PhotoRepository photoRepository;

  @CrossOrigin
  @RequestMapping(value = "add")
  public Result addHotel(@RequestBody HotelDto hotelDto){
    boolean flag;
    try {
      flag=hotelService.addHotel(hotelDto);
    } catch (Exception e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
    return ResultBuild.buildSuccessResult(flag);
  }

  @CrossOrigin
  @RequestMapping(value = "modify")
  public Result modifyHotel(@RequestBody HotelDto hotelDto){
    boolean flag;
    try {
      flag=hotelService.modifyHotel(hotelDto);
    } catch (Exception e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
    return ResultBuild.buildSuccessResult(flag);
  }

  @CrossOrigin
  @RequestMapping(value = "getHotelByAid")
  public Result getHotelByAid(@RequestBody String aid){
    HotelDto hotelDto = new HotelDto();
    try {
     hotelDto=hotelService.getHotelByAid(aid);
    } catch (Exception e) {
      ResultBuild.buildFailResult(e.getMessage());
    }
    return ResultBuild.buildSuccessResult(hotelDto);
  }

  @CrossOrigin
  @RequestMapping(value = "getReportSetByAid")
  public Result getReportSetByAid(@RequestBody String aid){
    ReportSetDto reportSetDto = new ReportSetDto();
    try {
      reportSetDto=hotelService.getReportSetByAid(aid);
    } catch (Exception e) {
      ResultBuild.buildFailResult(e.getMessage());
    }
    return ResultBuild.buildSuccessResult(reportSetDto);
  }

  @CrossOrigin
  @RequestMapping(value = "getReport")
  public Result getReport(@RequestBody ReportDateRangeDto rangeDto){

    if (rangeDto.getStartDate() == null) {
      return ResultBuild.buildFailResult(REPORT_DON_NOT_SELECT_DATE);
    }
    ReportDto reportDto=hotelService.getReport(rangeDto);

    return ResultBuild.buildSuccessResult(reportDto);
  }

  @CrossOrigin
  @RequestMapping(value = "uploadPic")
  public Result uploadPic(@RequestParam(value = "aid") String aid,
                          @RequestParam(value = "file") MultipartFile multipartFile) {
    if (multipartFile == null) {
      return ResultBuild.buildFailResult(FILE_IS_EMPTY_UPLOAD_FAILED);
    }
    InputStream is;
    try {
      is = multipartFile.getInputStream();
    } catch (IOException e) {
      return ResultBuild.buildFailResult(e.getMessage());
    }
    hotelService.uploadPic(is, aid);
    return ResultBuild.buildSuccessResult(true);
  }

  @CrossOrigin
  @RequestMapping(value = "showPic")
  public void showPic(@RequestParam long id, HttpServletResponse res) {
    Photo photo=(Photo)photoRepository.findById(Photo.class, id);
    try {
      res.getOutputStream().write(photo.getPicture());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
