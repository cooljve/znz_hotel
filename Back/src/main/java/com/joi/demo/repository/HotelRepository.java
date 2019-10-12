package com.joi.demo.repository;

import com.joi.demo.entity.Hotel;

public interface HotelRepository extends BaseRepository {
  public Hotel findHotel(Long id);
}
