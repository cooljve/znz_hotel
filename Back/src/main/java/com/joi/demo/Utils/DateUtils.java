package com.joi.demo.Utils;

import com.joi.demo.dto.ReportDateRangeDto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
  public static int getCurrMonth() {
    Calendar calendar = Calendar.getInstance();
    int month = calendar.get(Calendar.MONTH) + 1;
    return month;
  }

  public static String getCurrDay() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(Calendar.getInstance().getTime());
  }

  public static Date getCurrDayFirstTime(){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    return calendar.getTime();
  }

  public static Date getCurrDayLastTime(){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DATE,1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    return calendar.getTime();
  }

  public static Date getCurrMonthFirstDay() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    return calendar.getTime();
  }

  public static Date getCurrMonthLastDay() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, 1);
    calendar.set(Calendar.DAY_OF_MONTH, 0);
    return calendar.getTime();
  }

  public static int computeDays(Date start, Date end) {
    return (int) ((end.getTime() - start.getTime()) / (60 * 60 * 1000 * 24));
  }

  public static int computeHour(Date start, Date end) {
    long time=end.getTime() - start.getTime();
    long day=60 * 60 * 1000 * 24;
    long hour=60*60*1000;
    return (int) (time%day/hour);
  }

  public static void getEveryDayByStartAndEndDate(List<Long> dateList, Date[] date) {
    Date startDate = date[0];
    Date endDate = date[1];
    long start = startDate.getTime();
    long end = endDate.getTime();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(endDate);
    while (end >= start) {
      if (calendar.getTime().compareTo(endDate) == 0) {
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        end = calendar.getTimeInMillis();
        continue;
      }
      end = calendar.getTimeInMillis();
      calendar.set(Calendar.HOUR_OF_DAY,0);
      calendar.set(Calendar.MINUTE,0);
      calendar.set(Calendar.SECOND,0);
      dateList.add(calendar.getTime().getTime());
      calendar.add(Calendar.DAY_OF_MONTH,-1);
    }
  }
  public static boolean inSelectedDate(Date date, ReportDateRangeDto rangeDto) {
    if (date.compareTo(rangeDto.getStartDate()) < 0) {
      return false;
    }
    if (date.compareTo(rangeDto.getEndDate()) > 0) {
      return false;
    }
    return true;
  }

  public static boolean inSelectedDate(Date start,Date end, ReportDateRangeDto rangeDto) {
    if (start.compareTo(rangeDto.getStartDate()) < 0) {
      return false;
    }
    if (end.compareTo(rangeDto.getEndDate()) > 0) {
      return false;
    }
    return true;
  }
}
