package com.joi.demo.repository;

import com.joi.demo.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import static com.joi.demo.Utils.Constants.USER_NOT_EXIST;

@Repository
public class JpaUserRepository extends JpaBaseRepository implements UserRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public String findPasswordById(long uid) {
    String sql = "select u.password from User u where u.id=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, uid);
    String res = (String) query.getSingleResult();
    if (res == null) {
      return null;
    }
    return res;
  }

  @Override
  public User findByUserName(String userName) throws Exception{
    User user = null;
    String sql = "select u from User u where u.userName=?1";
    Query query = em.createQuery(sql);
    query.setParameter(1, userName);
    try {
      user = (User) query.getSingleResult();
    } catch (Exception e) {
      throw new Exception(USER_NOT_EXIST);
    }
    return user ;
  }
}
