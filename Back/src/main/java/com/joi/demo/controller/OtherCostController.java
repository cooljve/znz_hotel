package com.joi.demo.controller;

import com.joi.demo.Utils.Result;
import com.joi.demo.Utils.ResultBuild;
import com.joi.demo.dto.OtherCostDto;
import com.joi.demo.service.OtherCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/otherCost")
public class OtherCostController {

  @Autowired
  private OtherCostService otherCostService;

  @CrossOrigin
  @RequestMapping(value = "add")
  public Result addOtherCost(@RequestBody OtherCostDto otherCostDto) {
    boolean flag;
    flag = otherCostService.addOtherCost(otherCostDto);
    return ResultBuild.buildSuccessResult(flag);
  }

  @CrossOrigin
  @RequestMapping(value = "modify")
  public Result modifyOtherCost(@RequestBody OtherCostDto otherCostDto) {
    boolean flag;
    flag = otherCostService.modifyOtherCost(otherCostDto);
    return ResultBuild.buildSuccessResult(flag);
  }

  @CrossOrigin
  @RequestMapping(value = "delete")
  public Result deleteOtherCost(@RequestBody long ocid) {
    boolean flag;
    flag = otherCostService.deleteOtherCost(ocid);
    return ResultBuild.buildSuccessResult(flag);
  }

}
