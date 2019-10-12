package com.joi.demo.repository;

import com.joi.demo.dto.BookRoomDto;
import com.joi.demo.dto.RoomSearchCriteriaDto;
import com.joi.demo.entity.Hotel;
import com.joi.demo.entity.Order;
import com.joi.demo.entity.Room;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class JpaRoomRepository extends JpaBaseRepository implements RoomRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public int getMaxFloorNum(Hotel hotel, int floorNum) {
    String sql = "select max(r.roomNumber) from Room r where r.hotel=?1 and r.floorNum=?2";
    Query query = em.createQuery(sql);
    query.setParameter(1, hotel);
    query.setParameter(2, floorNum);
    if (query.getSingleResult() == null) {
      return floorNum * 100 - 1;
    }
    int res = (int) query.getSingleResult();
    return res;
  }

  @Override
  public List<Room> getAllRooms(Hotel hotel) {
    String sql = "select r from Room r where r.hotel=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, hotel);
    return query.getResultList();
  }

  @Override
  public List<Object[]> getRoomCount(Hotel hotel) {
    String sql = "select r.roomStatus,count(r) from Room r where r.hotel=?1 group by r.roomStatus";
    Query query = em.createQuery(sql);
    query.setParameter(1, hotel);
    List<Object[]> list = query.getResultList();
    return list;
  }

  @Transactional
  @Override
  public int delete(long rid) {
    String sql = "delete from Room r where r.rid=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, rid);
    return query.executeUpdate();
  }

  @Override
  public Room findByRoomNumberAndHotel(int roomNumber, Hotel hotel) {
    String sql = "select r from Room r where r.roomNumber=?1 and r.hotel=?2";
    Query query = em.createQuery(sql);
    query.setParameter(1, roomNumber);
    query.setParameter(2, hotel);
    if (query.getResultList().size() == 0) {
      return null;
    }
    return (Room) query.getSingleResult();
  }

  @Override
  public List<Room> searchRoom(RoomSearchCriteriaDto searchCriteria) {
    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
    CriteriaQuery<Room> query = criteriaBuilder.createQuery(Room.class);
    Root<Room> root = query.from(Room.class);
    query.select(root);
    List<Predicate> conditions = getPredicates(searchCriteria, criteriaBuilder, root);
    query.where(conditions.toArray(new Predicate[conditions.size()]));
    return em.createQuery(query).getResultList();
  }

  @Override
  public Room findFitRoom(BookRoomDto bookRoomDto) {
    String sql = "select r from Room r where r.roomType=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, bookRoomDto.getType());
    List<Room> roomList = query.getResultList();
    if (roomList.size() != 0) {
      for (Room room : roomList) {
        List<Order> orders = room.getOrders();
        if (isIncludeBookDate(orders, bookRoomDto)) {
          break;
        }
        return room;
      }
      return null;
    }
    return null;
  }

  private boolean isIncludeBookDate(List<Order> orders, BookRoomDto bookRoomDto) {
    Date sCurr = bookRoomDto.getOrder().getDate()[0];
    Date eCurr = bookRoomDto.getOrder().getDate()[1];
    for (Order order : orders) {
      Date sOld = order.getStartDate();
      Date eOld = order.getEndDate();
      if (sOld.compareTo(sCurr) <= 0 || eOld.compareTo(eCurr) >= 0) {
        return true;
      }
    }
    return false;
  }

  @Override
  public List<Order> getAllOrders(long rid) {
    String sql = "select r from Room r where r.rid=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, rid);
    if (query.getResultList().size() != 0) {
      Room room = (Room) query.getSingleResult();
      return room.getOrders();
    }
    return null;
  }

  private List<Predicate> getPredicates(RoomSearchCriteriaDto searchCriteria, CriteriaBuilder criteriaBuilder, Root<Room> root) {
    List<Predicate> conditions = new ArrayList<>();
    if (searchCriteria.getRoomNumber() != 0) {
      Predicate cond = criteriaBuilder.equal(root.get("roomNumber"), searchCriteria.getRoomNumber());
      conditions.add(cond);
    }
    if (searchCriteria.getStatus().size() != 0) {
      Predicate cond = criteriaBuilder.in(root.get("roomStatus")).value(searchCriteria.getStatus());
      conditions.add(cond);
    }
    if (searchCriteria.getType().size() != 0) {
      Predicate cond = criteriaBuilder.in(root.get("roomType")).value(searchCriteria.getType());
      conditions.add(cond);
    }
    if (searchCriteria.getMinPrice() != 0) {
      Predicate cond = criteriaBuilder.ge(root.get("roomPrice"), searchCriteria.getMinPrice());
      conditions.add(cond);
    }
    if (searchCriteria.getMaxPrice() != 0) {
      Predicate cond = criteriaBuilder.le(root.get("roomPrice"), searchCriteria.getMaxPrice());
      conditions.add(cond);
    }
    return conditions;
  }
}
