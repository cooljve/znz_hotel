package com.joi.demo.dto;

public class RoomCount {
  private long hid;
  private long total;
  private long emptyClean;
  private long emptyDirty;
  private long clean;
  private long dirty;

  public long getHid() {
    return hid;
  }

  public void setHid(long hid) {
    this.hid = hid;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public long getEmptyClean() {
    return emptyClean;
  }

  public void setEmptyClean(long emptyClean) {
    this.emptyClean = emptyClean;
  }

  public long getEmptyDirty() {
    return emptyDirty;
  }

  public void setEmptyDirty(long emptyDirty) {
    this.emptyDirty = emptyDirty;
  }

  public long getClean() {
    return clean;
  }

  public void setClean(long clean) {
    this.clean = clean;
  }

  public long getDirty() {
    return dirty;
  }

  public void setDirty(long dirty) {
    this.dirty = dirty;
  }
}
