package com.joi.demo.dto;

import java.util.Date;

public class ReportDateRangeDto {
  private String aid;
  private Date startDate;
  private Date endDate;

  public ReportDateRangeDto(String aid, Date startDate, Date endDate) {
    this.aid = aid;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public String getAid() {
    return aid;
  }

  public void setAid(String aid) {
    this.aid = aid;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
