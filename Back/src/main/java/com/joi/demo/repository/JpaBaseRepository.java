package com.joi.demo.repository;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class JpaBaseRepository<T> implements BaseRepository {

  @PersistenceContext
  private EntityManager em;

  @Transactional
  @Override
  public boolean save(Object entity) {
    boolean flag = false;
    try {
      em.persist(entity);
      flag = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flag;
  }

  @Transactional(readOnly = false)
  @Override
  public boolean batchInsert(List list) {
    boolean flag = true;
    for (Object o : list) {
      try {
        em.persist(o);
      } catch (Exception e) {
        e.printStackTrace();
        flag=false;
      }
    }
    return flag;
  }

  @Override
  public Object findById(Class aClass,Long id) {
    Object res=null;
    try {
      res=em.find(aClass, id);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return res;
  }

  @Override
  public List<T> findAll() {
    return null;
  }

  @Override
  public boolean delete(Object entity) {
    return false;
  }

  @Transactional
  @Override
  public boolean update(Object entity) {
    boolean flag = false;
    try {
      em.merge(entity);
      em.flush();
      flag = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return flag;
  }
}
