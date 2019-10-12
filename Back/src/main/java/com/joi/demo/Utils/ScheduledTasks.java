package com.joi.demo.Utils;

import com.joi.demo.dto.ReportDateRangeDto;
import com.joi.demo.dto.ReportDto;
import com.joi.demo.entity.Admin;
import com.joi.demo.entity.Order;
import com.joi.demo.entity.Room;
import com.joi.demo.repository.AdminRepository;
import com.joi.demo.repository.OrderRepository;
import com.joi.demo.repository.RoomRepository;
import com.joi.demo.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

import static com.joi.demo.Utils.Constants.*;
import static com.joi.demo.Utils.Convert.getChinaTime;
import static com.joi.demo.Utils.DateUtils.*;
import static com.joi.demo.Utils.EmailUtils.*;

@Component
public class ScheduledTasks {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private HotelService hotelService;

  //  @Scheduled(cron = "0 0/5 10,11 * * ?")
  @Scheduled(cron = "0 0/2 17 * * ?")
  public void sendEmailToCus() {
    List<Order> orderList = orderRepository.findAllOrders();
    List<Admin> adminList = adminRepository.findAllAdmin();
    for (Admin admin : adminList) {
      String server = admin.getEmailAddress().split("@")[1];
      for (Order order : orderList) {
        if (isAdminOrder(admin, order)) {
          String smtp = EMAIL_SMTP + server;
          Session session = getSmtpSession(smtp);
          //同意邮件通知并且状态为预订的订单且是今日开始入住的
          if (order.isInform() && isBook(order) && isTodayCheckIn(order)) {
            MimeMessage msg;
            try {
              msg = createCustomerMail(session, order, false);
              sendMailFromAdmin(msg, session, order, smtp);
            } catch (Exception e) {
              e.printStackTrace();
            }
            Room r = order.getRoom();
            order.setStatus(ORDER_STATUS_CHECKIN);
            r.setRoomStatus("C");
            orderRepository.update(order);
          }
        }
      }
    }
  }

  private boolean isAdminOrder(Admin admin, Order order) {
    return order.getRoom().getHotel().getAdmin().getAid().equals(admin.getAid());
  }

  @Scheduled(cron = "0 0 0 1 * ?")
  public void sendMonthReportToAdmin() {
    Date start = getCurrMonthFirstDay();
    Date end = getCurrMonthLastDay();
    List<Admin> adminList = adminRepository.findAllAdmin();
    Session session = getSmtpSession(SMTP_163);
    for (Admin admin : adminList) {
      ReportDto reportDto = hotelService.getReport(
          new ReportDateRangeDto(admin.getAid(), start, end));
      MimeMessage msg;
      try {
        msg = createAdminMailByMonth(session, admin, reportDto);
        sendMail(msg, session, SMTP_163);
        System.out.println("定时任务启动，发送月报邮件给酒店管理员。");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Scheduled(cron = "0 0/2 16 * * ?")
  public void sendDayReportToAdmin() {
    Date start = getCurrDayFirstTime();
    Date end = getCurrDayLastTime();
    List<Admin> adminList = adminRepository.findAllAdmin();
    Session session = getSmtpSession(SMTP_163);
    for (Admin admin : adminList) {
      ReportDto reportDto = hotelService.getReport(
          new ReportDateRangeDto(admin.getAid(), start, end));
      MimeMessage msg;
      try {
        msg = createAdminMailByDay(session, admin, reportDto);
        sendMail(msg, session, SMTP_163);
        System.out.println("定时任务启动，发送日报邮件给酒店管理员。");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Transactional
//  @Scheduled(cron = "0 0/5 13 * * ?")
  @Scheduled(cron = "0 0/2 17 * * ?")
  public void autoCheckOut() {
    List<Order> orderList = orderRepository.findAllOrders();
    List<Admin> adminList = adminRepository.findAllAdmin();
    for (Admin admin : adminList) {
      String server = admin.getEmailAddress().split("@")[1];
      for (Order order : orderList) {
        if (isAdminOrder(admin, order) && isCheckOut(order)) {
          String smtp = EMAIL_SMTP + server;
          Session session = getSmtpSession(smtp);
          MimeMessage msg;
          try {
            msg = createCustomerMail(session, order, true);
            sendMailFromAdmin(msg, session, order, smtp);
            System.out.println("定时任务启动，发送退房邮件给住客。");
          } catch (Exception e) {
            e.printStackTrace();
          }
          Room r = order.getRoom();
          r.setRoomStatus("ED");
          order.setStatus(ORDER_STATUS_CHECKOUT);
          System.out.println("成功修改房间状态");
          roomRepository.update(r);
        }
      }
    }
  }

  private boolean isCheckOut(Order order) {
    return getChinaTime().compareTo(order.getEndDate()) >= 0 && order.getStatus().equals(ORDER_STATUS_CHECKIN);
  }

  private boolean isTodayCheckIn(Order order) {
    int hour = computeHour(getChinaTime(), order.getStartDate());
    return hour<=2;
  }

  private boolean isBook(Order order) {
    return order.getStartDate().compareTo(getChinaTime()) > 0 && order.getStatus().equals(ORDER_STATUS_BOOK);
  }

}
