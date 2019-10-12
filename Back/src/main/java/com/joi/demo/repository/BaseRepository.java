package com.joi.demo.repository;

import java.util.List;

public interface BaseRepository<T> {
  boolean save(T entity);

  boolean batchInsert(List<T> list);

  T findById(java.lang.Class<T> aClass,Long id);

  List<T> findAll();

  boolean delete(T entity);

  boolean update(T entity);
}
