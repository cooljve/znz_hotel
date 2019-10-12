package com.joi.demo.repository;

import com.joi.demo.entity.RoomType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class JpaRoomTypeRepository extends JpaBaseRepository implements RoomTypeRepository {

  @PersistenceContext
  private EntityManager em;

  @Transactional
  @Override
  public int delete(long rtid) {
    String sql = "delete from RoomType rt where rt.rtid=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, rtid);
    return query.executeUpdate();
  }

  @Override
  public RoomType findByRtid(long rtid) {
    String sql = "select rt from RoomType rt where rt.rtid=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, rtid);
    if (query.getResultList().size() == 0) {
      return null;
    }
    return (RoomType) query.getSingleResult();
  }
}
