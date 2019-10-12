package com.joi.demo.repository;

import com.joi.demo.entity.Admin;

import java.util.List;

public interface AdminRepository extends BaseRepository {

  public String findPasswordByAid(String aid);

  public Admin findByAid(String aid) ;

  public List<Admin> findAllAdmin();
}
