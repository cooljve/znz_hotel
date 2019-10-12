package com.joi.demo.service;

import com.joi.demo.dto.RoomTypeDto;
import com.joi.demo.entity.Hotel;
import com.joi.demo.entity.RoomType;
import com.joi.demo.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joi.demo.Utils.Convert.convertToRoomType;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {
  @Autowired
  private RoomTypeRepository roomTypeRepository;

  @Override
  public boolean addRoomType(RoomTypeDto roomTypeDto) {
    Hotel hotel = (Hotel) roomTypeRepository.findById(Hotel.class, roomTypeDto.getHid());
    RoomType roomType = new RoomType();
    roomType.setHotel(hotel);
    convertToRoomType(roomType, roomTypeDto);
    return roomTypeRepository.save(roomType);
  }

  @Override
  public boolean modifyRoomType(RoomTypeDto roomTypeDto) {
    RoomType roomType = roomTypeRepository.findByRtid(roomTypeDto.getRtid());
    convertToRoomType(roomType, roomTypeDto);
    return roomTypeRepository.update(roomType);
  }

  @Override
  public boolean deleteRoomType(long rtid) {
    int count = roomTypeRepository.delete(rtid);
    return count == 1;
  }
}
