package com.joi.demo.repository;

import com.joi.demo.entity.Admin;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import static com.joi.demo.Utils.Constants.USER_NOT_EXIST;

@Repository
public class JpaAdminRepository extends JpaBaseRepository implements AdminRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public String findPasswordByAid(String aid) {
    String sql = "select a.password from Admin a where a.aid=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, aid);
    String res = (String) query.getSingleResult();
    if (res == null) {
      return null;
    }
    return res;
  }

  @Override
  public Admin findByAid(String aid){
    String sql = "select a from Admin a where a.aid=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, aid);
    if(query.getResultList().size()==0){
      return null;
    }
    return (Admin) query.getSingleResult();
  }

  @Override
  public List<Admin> findAllAdmin() {
    String sql = "select a from Admin a";
    Query query = em.createQuery(sql);
    return query.getResultList();
  }
}
