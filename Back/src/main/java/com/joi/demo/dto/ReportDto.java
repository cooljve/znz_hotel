package com.joi.demo.dto;

import java.util.List;

public class ReportDto {

  private List<ReportTableDto> expendReport;
  private List<ReportTableDto> incomeReport;

  public ReportDto(List<ReportTableDto> expendReport, List<ReportTableDto> incomeReport) {
    this.expendReport = expendReport;
    this.incomeReport = incomeReport;
  }

  public List<ReportTableDto> getExpendReport() {
    return expendReport;
  }

  public void setExpendReport(List<ReportTableDto> expendReport) {
    this.expendReport = expendReport;
  }

  public List<ReportTableDto> getIncomeReport() {
    return incomeReport;
  }

  public void setIncomeReport(List<ReportTableDto> incomeReport) {
    this.incomeReport = incomeReport;
  }
}
