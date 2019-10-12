package com.joi.demo.service;

import com.joi.demo.dto.OrderDto;
import com.joi.demo.dto.ReportDateRangeDto;
import com.joi.demo.dto.RoomDto;
import com.joi.demo.entity.Admin;
import com.joi.demo.entity.Hotel;
import com.joi.demo.entity.Order;
import com.joi.demo.entity.Room;
import com.joi.demo.repository.AdminRepository;
import com.joi.demo.repository.OrderRepository;
import com.joi.demo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.joi.demo.Utils.Constants.*;
import static com.joi.demo.Utils.Convert.*;
import static com.joi.demo.Utils.DateUtils.computeDays;
import static com.joi.demo.Utils.DateUtils.inSelectedDate;
import static com.joi.demo.Utils.EmailUtils.createCustomerMail;
import static com.joi.demo.Utils.EmailUtils.getSmtpSession;
import static com.joi.demo.Utils.EmailUtils.sendMailFromAdmin;

@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private RoomRepository roomRepository;

  @Override
  public boolean checkIn(RoomDto roomDto) {
    Room room = (Room) roomRepository.findById(Room.class, roomDto.getRid());
    Order order = new Order();
    if (roomDto.getOrder().getDate()[0].compareTo(roomDto.getOrder().getDate()[1]) == 0) {
      onlyOneDay(roomDto.getOrder());
    }
    convertToOrder(order, roomDto.getOrder());
    double earning = room.getRoomPrice() * computeDays(roomDto.getOrder().getDate()[0], roomDto.getOrder().getDate()[1]);
    order.setEarnings(earning);
    order.setCreateDate(getChinaTime());
    if (order.getStartDate().compareTo(getChinaTime())>0) {
      order.setStatus(ORDER_STATUS_BOOK);
    }else {
      order.setStatus(ORDER_STATUS_CHECKIN);
    }
    order.setFrontUserName(roomDto.getOrder().getFrontUserName());
    room.getOrders().add(order);
    if (order.getStartDate().compareTo(getChinaTime())<=0) {
      room.setRoomStatus("C");
    }
    return roomRepository.update(room);
  }

  private void onlyOneDay(OrderDto order) {
    Date[] dates = new Date[2];
    dates[0] = order.getDate()[0];
    Date date = order.getDate()[1];
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    date = calendar.getTime();
    dates[1] = date;
    order.setDate(dates);
  }

  @Override
  public double checkOut(RoomDto roomDto) {
    Room room = (Room) roomRepository.findById(Room.class, roomDto.getRid());
    room.setRoomStatus("ED");
    Order order = (Order) orderRepository.findById(Order.class, roomDto.getOrder().getOid());
    Date now = getChinaTime();
    Date endTime = order.getEndDate();
    double res = 0;
    if (now.compareTo(endTime) <= 0) {
      int days = computeDays(now, endTime);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(now);
      days = calendar.get(Calendar.HOUR_OF_DAY) >= 12 ? --days : days;
      res = days * roomDto.getPrice() * 50 / 100;
    }
    order.setEndDate(now);
    order.setEarnings(order.getEarnings() - res);
    order.setGrade(roomDto.getOrder().getRate());
    order.setStatus(ORDER_STATUS_CHECKOUT);
    order.setFrontUserName(roomDto.getOrder().getFrontUserName());
    computeHotelGrade(room.getHotel());
    roomRepository.update(room);
    return res;
  }

  public static void computeHotelGrade(Hotel hotel) {
    int count = 0;
    double totalGrade = 0;
    for (Room room : hotel.getRooms()) {
      for (Order roomOrder : room.getOrders()) {
        totalGrade += roomOrder.getGrade();
        if (roomOrder.getEndDate().compareTo(getChinaTime()) <= 0 && roomOrder.getGrade() != 0) {
          count++;
        }
      }
    }
    hotel.setGrade(totalGrade / count);
  }

  @Override
  public boolean modify(RoomDto roomDto) {
    Order order = (Order) orderRepository.findById(Order.class, roomDto.getOrder().getOid());
    convertToOrder(order, roomDto.getOrder());
    double earning = roomDto.getPrice() * computeDays(roomDto.getOrder().getDate()[0], roomDto.getOrder().getDate()[1]);
    order.setEarnings(earning);
    return orderRepository.update(order);
  }

  @Override
  public List<OrderDto> getAllOrders(String aid) {
    Admin admin = getAdmin(aid);
    List<OrderDto> orderDtos = new ArrayList<>();
    List<Room> rooms = admin.getHotel().getRooms();
    int i = 1;
    for (Room room : rooms) {
      for (Order order : room.getOrders()) {
        OrderDto orderDto = new OrderDto();
        convertToOrderDto(orderDto, order);
        orderDto.setSerialNum(i++);
        orderDtos.add(orderDto);
      }
    }
    return orderDtos;
  }

  @Override
  public boolean sendEmailToCus(OrderDto orderDto) throws Exception {
    Order order = (Order) orderRepository.findById(Order.class, orderDto.getOid());
    Admin admin = order.getRoom().getHotel().getAdmin();
    String server = admin.getEmailAddress().split("@")[1];
    String smtp = EMAIL_SMTP + server;
    Session session = getSmtpSession(smtp);
    try {
      MimeMessage msg = createCustomerMail(session, order, false);
      sendMailFromAdmin(msg, session, order, smtp);
    } catch (Exception e) {
      throw new Exception(EMAIL_SEND_FAILED);
    }
    return true;
  }

  @Override
  public List<OrderDto> getOrdersByDate(ReportDateRangeDto dateRangeDto) {
    Admin admin = getAdmin(dateRangeDto.getAid());
    List<OrderDto> orderDtos = new ArrayList<>();
    List<Room> rooms = admin.getHotel().getRooms();
    int i = 1;
    for (Room room : rooms) {
      for (Order order : room.getOrders()) {
        if (inSelectedDate(order.getStartDate(),order.getEndDate(), dateRangeDto)) {
          OrderDto orderDto = new OrderDto();
          convertToOrderDto(orderDto, order);
          orderDto.setSerialNum(i++);
          orderDtos.add(orderDto);
        }
      }
    }
    return orderDtos;
  }

  private Admin getAdmin(String aid) {
    Admin admin = new Admin();
    try {
      admin = adminRepository.findByAid(aid);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return admin;
  }
}
