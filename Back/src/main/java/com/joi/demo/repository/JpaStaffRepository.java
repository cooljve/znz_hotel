package com.joi.demo.repository;

import com.joi.demo.entity.Staff;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class JpaStaffRepository extends JpaBaseRepository implements StaffRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Staff findBySid(long sid) {
    String sql = "select s from Staff s where s.sid=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, sid);
    if (query.getResultList().size() == 0) {
      return null;
    }
    return (Staff) query.getSingleResult();
  }

  @Transactional
  @Override
  public int delete(long sid) {
    String sql = "delete from Staff s where s.sid=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, sid);
    return query.executeUpdate();
  }
}
