package com.joi.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="znz_other_cost")
public class OtherCost implements Serializable {

  private static final long serialVersionUID = -6975915429503288257L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ocid;

  private String description;

  private double cost;

  @Column(name = "is_expend")
  private boolean out;

  private Date date;

  @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
  @JoinColumn(name = "hid")
  @JsonBackReference
  private Hotel hotel;

  public Long getOcid() {
    return ocid;
  }

  public void setOcid(Long ocid) {
    this.ocid = ocid;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String desc) {
    this.description = desc;
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  public boolean isOut() {
    return out;
  }

  public void setOut(boolean out) {
    this.out = out;
  }
}
