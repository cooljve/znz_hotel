package com.joi.demo.Utils;

import com.joi.demo.dto.ReportDto;
import com.joi.demo.dto.ReportTableDto;
import com.joi.demo.entity.Admin;
import com.joi.demo.entity.Order;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static com.joi.demo.Utils.Constants.*;
import static com.joi.demo.Utils.DateUtils.getCurrDay;
import static com.joi.demo.Utils.DateUtils.getCurrMonth;

public class EmailUtils {

  public static Session getSmtpSession(String string) {
    Properties props = new Properties();
    props.setProperty("mail.transport.protocol", "smtp");
    props.setProperty("mail.smtp.host", string);
    props.setProperty("mail.smtp.auth", "true");

    Session session = Session.getInstance(props);
    session.setDebug(true);
    return session;
  }

  public static MimeMessage createCustomerMail(Session session, Order o, boolean checkOut) throws Exception {
    Admin admin = getAdmin(o);
    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(admin.getEmailAddress()));
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(o.getCustomer().getEmailAddress()));
    if (checkOut) {
      message.setSubject(EMAIL_SUBJECT_TO_CUS_CHECK_OUT);
      message.setContent(buildCusCheckOutEmail(o), TEXT_HTML_UTF8);
    } else {
      message.setSubject(EMAIL_SUBJECT_TO_CUS_CHECK_IN);
      message.setContent(buildCusEmailContent(o), TEXT_HTML_UTF8);
    }
    return message;
  }

  public static void sendMailFromAdmin(MimeMessage msg, Session session, Order order, String smtp) throws Exception {
    Admin admin = getAdmin(order);
    Transport transport = session.getTransport();
    transport.connect(smtp, admin.getEmailAddress(), admin.getEmailPwd());
    transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
    transport.close();
  }

  public static MimeMessage createAdminMailByMonth(Session session, Admin admin, ReportDto reportDto) throws Exception {
    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(EMAIL_ADDRESS));
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(admin.getEmailAddress()));
    message.setSubject(EMAIL_SUBJECT_TO_ADMIN_MONTH);
    message.setContent(buildMonthReport(admin, reportDto), TEXT_HTML_UTF8);
    return message;
  }

  public static MimeMessage createAdminMailByDay(Session session, Admin admin, ReportDto reportDto) throws Exception {
    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(EMAIL_ADDRESS));
    message.setRecipient(Message.RecipientType.TO, new InternetAddress(admin.getEmailAddress()));
    message.setSubject(EMAIL_SUBJECT_TO_ADMIN_DAY);
    message.setContent(buildDayReport(admin, reportDto), TEXT_HTML_UTF8);
    return message;
  }

  public static void sendMail(MimeMessage msg, Session session, String smtp) throws Exception {
    Transport transport = session.getTransport();
    transport.connect(smtp, EMAIL_ADDRESS, EMAIL_PWD);
    transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
    transport.close();
  }

  private static Object buildDayReport(Admin admin, ReportDto reportDto) {
    StringBuilder sb = new StringBuilder("<html><head></head><body><h2>收支情况</h2>");
    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;尊敬的客户，您所管理的酒店");
    sb.append(admin.getHotel().getHotelName());
    sb.append("，");
    sb.append(getCurrDay());
    sb.append("的财务收支情况如下：<br>");
    getTableData(reportDto, sb);
    return sb.toString();
  }

  private static Object buildMonthReport(Admin admin, ReportDto reportDto) {
    StringBuilder sb = new StringBuilder("<html><head></head><body><h2>收支情况</h2>");
    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;尊敬的客户，您所管理的酒店");
    sb.append(admin.getHotel().getHotelName());
    sb.append("，");
    sb.append(getCurrMonth());
    sb.append("月的财务收支情况如下：<br>");
    getTableData(reportDto, sb);
    return sb.toString();
  }

  private static void buildTable(StringBuilder sb, ReportTableDto expend) {
    sb.append("<tr>");
    sb.append("<td>" + expend.getSerialNum() + "</td>");
    sb.append("<td>" + expend.getName() + "</td>");
    sb.append("<td>" + expend.getMoney() + "</td>");
    sb.append("</tr>");
  }

  private static Object buildCusEmailContent(Order o) {
    StringBuilder sb = new StringBuilder();
    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;尊敬的");
    sb.append(o.getCustomer().getRealName());
    sb.append("，您预订的");
    sb.append(o.getRoom().getHotel().getHotelName());
    sb.append(o.getRoom().getRoomNumber());
    sb.append("号房间");
    sb.append("将于");
    sb.append(o.getStartDate());
    sb.append("入住，");
    sb.append("您的房间开门密码为");
    sb.append(o.getDoorPassword());
    sb.append(",请注意保管。如有疑问请联系前台工作人员。<br><br><br>");
    sb.append("祝您有个愉快的住宿体验。<br>");
    sb.append(o.getRoom().getHotel().getHotelName());
    return sb.toString();
  }

  private static Object buildCusCheckOutEmail(Order o) {
    StringBuilder sb = new StringBuilder();
    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;尊敬的");
    sb.append(o.getCustomer().getRealName());
    sb.append("，您的");
    sb.append(o.getRoom().getHotel().getHotelName());
    sb.append(o.getRoom().getRoomNumber());
    sb.append("号房间");
    sb.append("已于");
    sb.append(o.getEndDate());
    sb.append("成功退房。");
    sb.append("如有疑问请联系前台工作人员。<br><br><br>");
    sb.append("欢迎您的下次光临。<br>");
    sb.append(o.getRoom().getHotel().getHotelName());
    return sb.toString();
  }

  private static Admin getAdmin(Order order) {
    return order.getRoom().getHotel().getAdmin();
  }

  public static String getRandomString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 6; i++) {
      sb.append(getRandom());
    }
    return sb.toString();
  }

  private static int getRandom() {
    return (int) (Math.random() * 9);
  }

  private static void getTableData(ReportDto reportDto, StringBuilder sb) {
    sb.append("<h3>支出：<h3>");
    sb.append("<table border=\"5\" style=\"border:solid 1px #E8F2F9;font-size=14px;;font-size:18px;\">");
    sb.append("<tr><th>序号</th><th>描述</th><th>金额</th></tr>");
    double zhichu = 0, shouru = 0;
    for (ReportTableDto expend : reportDto.getExpendReport()) {
      zhichu += expend.getMoney();
      buildTable(sb, expend);
    }
    sb.append("</table>");
    sb.append("<h3>收入：</h3>");
    sb.append("<table border=\"5\" style=\"border:solid 1px #E8F2F9;font-size=14px;;font-size:18px;\">");
    sb.append("<tr><th>序号</th><th>描述</th><th>金额</th></tr>");
    for (ReportTableDto income : reportDto.getIncomeReport()) {
      shouru += income.getMoney();
      buildTable(sb, income);
    }
    sb.append("</table>");
    sb.append("<br>总利润为");
    double res = shouru - zhichu;
    sb.append(res);
    sb.append("元。");
    sb.append("<br>如有疑问请联系系统管理员。<br><br><br>");
    sb.append("</body></html>");
  }
}
