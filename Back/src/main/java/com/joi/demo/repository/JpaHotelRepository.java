package com.joi.demo.repository;

import com.joi.demo.entity.Hotel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class JpaHotelRepository extends JpaBaseRepository implements HotelRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Hotel findHotel(Long id) {
    String sql = "select h from Hotel h where h.hid=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, id);
    Hotel hotel = (Hotel) query.getSingleResult();
    return hotel;
  }
}
