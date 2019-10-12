package com.joi.demo.Utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static com.joi.demo.Utils.DateUtils.computeHour;
import static org.junit.Assert.*;

public class DateUtilsTest {

  @Test
  public void testComputeDay() {
    Date now = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(now);
    calendar.add(Calendar.HOUR,21);
    Date end = calendar.getTime();
    int hour=computeHour(now, end);
    assertEquals(21, hour);
  }
}