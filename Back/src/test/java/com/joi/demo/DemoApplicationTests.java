package com.joi.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joi.demo.Utils.testClass;
import com.joi.demo.dto.ReportDateRangeDto;
import com.joi.demo.dto.ReportDto;
import com.joi.demo.dto.RoomsDto;
import com.joi.demo.entity.*;
import com.joi.demo.repository.AdminRepository;
import com.joi.demo.repository.OtherCostRepository;
import com.joi.demo.repository.RoomRepository;
import com.joi.demo.service.HotelService;
import com.joi.demo.service.RoomService;
import com.joi.demo.service.RoomServiceImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.joi.demo.Utils.Constants.ORDER_STATUS_CHECKIN;
import static com.joi.demo.Utils.Constants.SMTP_163;
import static com.joi.demo.Utils.DateUtils.*;
import static com.joi.demo.Utils.EmailUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

  private ObjectMapper objMapper = new ObjectMapper();

  private RoomRepository roomRepository;

  private RoomService roomService = new RoomServiceImpl();

  private AdminRepository adminRepository;

  private HotelService hotelService;

  @Before
  public void setUp() {
    roomRepository = mock(RoomRepository.class);
    adminRepository = mock(AdminRepository.class);
    setField(roomService,"adminRepository",adminRepository);
    setField(roomService,"roomRepository",roomRepository);
  }

  @Test
  public void contextLoads() {
    List<testClass> list = new ArrayList<>();
    testClass a = new testClass();
    a.setCost(100);
    a.setId(1);
    a.setName("abc");
    list.add(a);
    list.add(a);
    String str;
    try {
      str = objMapper.writeValueAsString(list);
      System.out.println(str);
      List<testClass> strs = objMapper.readValue(str, new TypeReference<List<testClass>>() {
      });
      System.out.println(strs);
      testClass b = strs.get(0);
      b.getCost();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetRoomCount() throws Exception {
    when(roomRepository.getAllRooms(any())).thenReturn(expectRoomList());
    when(roomRepository.getRoomCount(any())).thenReturn(expectCountList());
    when(adminRepository.findByAid(anyString())).thenReturn(expectAdmin());

    RoomsDto roomsDto = roomService.getAll("dunshan");

    assertEquals("标准间",roomsDto.getRoomTypeDtos().get(0).getRoomType());
    assertEquals("wifi",roomsDto.getRoomDtoList().get(0).getConfiguration().get(0));
    assertEquals(3,roomsDto.getRoomDtoList().get(0).getConfiguration().size());
  }

  private Admin expectAdmin() {
    Admin admin = new Admin();
    admin.setHotel(expectHotel());
    return admin;
  }

  private Hotel expectHotel() {
    Hotel hotel = new Hotel();
    hotel.setRoomTypes(expectRoomTypeList());
    hotel.setHid(1L);
    return hotel;
  }

  private List<RoomType> expectRoomTypeList() {
    List<RoomType> roomTypeList = new ArrayList<>();
    roomTypeList.add(expectRoomType("标准间"));
    return roomTypeList;
  }

  private RoomType expectRoomType(String str) {
    RoomType type = new RoomType();
    type.setBedCount(2);
    type.setRoomType(str);
    Hotel hotel = new Hotel();
    hotel.setHid(1L);
    type.setHotel(hotel);
    type.setRtid(1L);
    return type;
  }

  private List<Object[]> expectCountList() {
    List<Object[]> list = new ArrayList<>();
    return list;

  }

  private List<Room> expectRoomList(){
    List<Room> roomList = new ArrayList<>();
    Room room = expectRoom();
    roomList.add(room);
    return roomList;
  }

  private Room expectRoom() {
    Room room = new Room();
    room.setFloorNum(1);
    room.setRoomNumber(100);
    room.setRoomType("标准间");
    room.setRoomStatus("EC");
    room.setRoomPrice(100);
    room.setConfiguration("[\"wifi\",\"空调\",\"独卫\"]");
    room.setRid(1L);
    room.setOrders(expectOrderList());
    return room;
  }

  private List<Order> expectOrderList() {
    List<Order> orderList = new ArrayList<>();
    orderList.add(expectOrder());
    return orderList;
  }

  private Order expectOrder() {
    Order order = new Order();
    order.setCreateDate(new Date());
    order.setStatus(ORDER_STATUS_CHECKIN);
    order.setStartDate(new Date());
    order.setEarnings(200);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.set(Calendar.HOUR,12);
    order.setEndDate(calendar.getTime());
    order.setFrontUserName("xiaodunshan");
    order.setCustomer(expectCustomer());
    order.setOid(1L);
    Room room = new Room();
    room.setRoomNumber(100);
    order.setRoom(room);
    return order;
  }

  private Customer expectCustomer() {
    Customer customer = new Customer();
    customer.setEmailAddress("123");
    customer.setIdCard("123");
    customer.setRealName("123");
    return customer;
  }

  @Test
  public void testRoomNumber() throws Exception {
    Date now = new Date();
    System.out.println(now);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
    sdf.setTimeZone(timeZone);
    String str = sdf.format(now);
    System.out.println(str);
  }

  @Transactional
  @Ignore
  @Test
  public void testSendEmailToAdmin() throws Exception {
    Admin admin = adminRepository.findByAid("dunshan");
    Date start = getCurrDayFirstTime();
    Date end = getCurrDayLastTime();
    Session session = getSmtpSession(SMTP_163);
    ReportDto reportDto = hotelService.getReport(
        new ReportDateRangeDto(admin.getAid(), start, end));
    MimeMessage msg;
    try {
      msg = createAdminMailByDay(session, admin, reportDto);
      sendMail(msg, session, SMTP_163);
      System.out.println("定时任务启动，发送邮件给酒店管理员。");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetEveryDayByStartAndEndDate() throws Exception {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.DAY_OF_MONTH,3);
    Date[] dates = new Date[]{new Date(),calendar.getTime()};
    List<Long> dateList = new ArrayList<>();
    getEveryDayByStartAndEndDate(dateList, dates);
    int hour=calendar.get(Calendar.HOUR_OF_DAY);
    assertEquals(3,dateList.size());
  }
}
