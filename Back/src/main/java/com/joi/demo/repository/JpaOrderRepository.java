package com.joi.demo.repository;

import com.joi.demo.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaOrderRepository extends JpaBaseRepository implements OrderRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public List<Order> findAllOrders() {
    String sql = "select o from Order o ";
    Query query = em.createQuery(sql);
    return query.getResultList();
  }
}
