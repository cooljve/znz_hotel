package com.joi.demo.repository;

import com.joi.demo.entity.FrontUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class JpaFrontUserRepository extends JpaBaseRepository implements FrontUserRepository {
  @PersistenceContext
  private EntityManager em;

  @Override
  public FrontUser findByUserName(String userName) {
    FrontUser user = null;
    String sql = "select u from FrontUser u where u.userName=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, userName);
    if (query.getResultList().size() == 0) {
      return null;
    }
    return (FrontUser) query.getSingleResult();
  }

  @Transactional
  @Override
  public int delete(String userName) {
    String sql = "delete from FrontUser u where u.userName=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, userName);
    return query.executeUpdate();
  }
}
