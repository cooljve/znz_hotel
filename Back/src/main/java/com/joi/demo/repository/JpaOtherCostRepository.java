package com.joi.demo.repository;

import com.joi.demo.entity.OtherCost;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class JpaOtherCostRepository extends JpaBaseRepository implements OtherCostRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public OtherCost findByOcid(long ocid) {
    String sql = "select oc from OtherCost oc where oc.ocid=?1";
    Query query=em.createQuery(sql);
    query.setParameter(1, ocid);
    if (query.getResultList().size() == 0) {
      return null;
    }
    return (OtherCost) query.getSingleResult();
  }

  @Transactional
  @Override
  public int delete(long ocid) {
    String sql = "delete from OtherCost oc where oc.ocid=?1 ";
    Query query = em.createQuery(sql);
    query.setParameter(1, ocid);
    return query.executeUpdate();
  }
}
