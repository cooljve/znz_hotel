package com.joi.demo.service;

import com.joi.demo.dto.OtherCostDto;
import com.joi.demo.entity.Hotel;
import com.joi.demo.entity.OtherCost;
import com.joi.demo.repository.OtherCostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.joi.demo.Utils.Convert.convertToOtherCost;

@Service
public class OtherCostServiceImpl implements OtherCostService {

  @Autowired
  private OtherCostRepository otherCostRepository;

  @Override
  public boolean addOtherCost(OtherCostDto otherCostDto) {
    Hotel hotel = (Hotel) otherCostRepository.findById(Hotel.class, otherCostDto.getHid());
    OtherCost otherCost = new OtherCost();
    otherCost.setHotel(hotel);
    convertToOtherCost(otherCost, otherCostDto);
    return otherCostRepository.save(otherCost);
  }

  @Override
  public boolean modifyOtherCost(OtherCostDto otherCostDto) {
    OtherCost otherCost = otherCostRepository.findByOcid(otherCostDto.getOcid());
    convertToOtherCost(otherCost,otherCostDto);
    return otherCostRepository.update(otherCost);
  }

  @Override
  public boolean deleteOtherCost(long ocid) {
    int count = otherCostRepository.delete(ocid);
    return count == 1;
  }
}
