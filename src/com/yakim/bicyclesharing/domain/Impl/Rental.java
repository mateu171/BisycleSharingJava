package com.yakim.bicyclesharing.domain.Impl;

import com.yakim.bicyclesharing.domain.BaseEntity;
import com.yakim.bicyclesharing.domain.exeption.CustomEntityValidationExeption;
import java.util.Date;
import java.util.UUID;

public class Rental extends BaseEntity {

  private UUID userId;
  private UUID bicycleId;
  private UUID startStationId;
  private UUID endStationId;
  private Date start;
  private Date end;
  private double totalCost;

  private Rental() {
    super();
  }

  public Rental(UUID userId, UUID bicycleId, UUID startStationId, Date start, String totalCost) {
    this();
    setUserId(userId);
    setBicycleId(bicycleId);
    setStartStationId(startStationId);
    setStart(start);
    setTotalCost(totalCost);

    if (!isValid()) {
      throw new CustomEntityValidationExeption(getErrors());
    }
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    cleanErrors("userId");
    if (userId == null) {
      addError("userId", "Користувач не може бути null!");
    }
    this.userId = userId;
  }

  public UUID getBicycleId() {
    return bicycleId;
  }

  public void setBicycleId(UUID bicycleId) {
    cleanErrors("bicycleId");
    if (bicycleId == null) {
      addError("bicycleId", "Велосипед не може бути null!");
    }
    this.bicycleId = bicycleId;
  }

  public UUID getStartStationId() {
    return startStationId;
  }

  public void setStartStationId(UUID startStationId) {
    cleanErrors("startStationId");
    if (startStationId == null) {
      addError("startStationId", "Початкова станція не може бути null!");
    }
    this.startStationId = startStationId;
  }

  public UUID getEndStationId() {
    return endStationId;
  }

  public void setEndStationId(UUID endStationId) {
    cleanErrors("endStationId");
    this.endStationId = endStationId; // кінцева станція може бути null поки оренда триває
  }

  public Date getStart() {
    return start;
  }

  public void setStart(Date start) {
    cleanErrors("start");
    if (start == null) {
      addError("start", "Дата початку не може бути null!");
    }
    this.start = start;
  }

  public Date getEnd() {
    return end;
  }

  public void setEnd(Date end) {
    cleanErrors("end");
    if (end != null && start != null && end.before(start)) {
      addError("end", "Дата завершення не може бути раніше дати початку!");
    }
    this.end = end;
  }

  public double getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(String totalCostStr) {
    cleanErrors("totalCost");

    if (totalCostStr == null || totalCostStr.trim().isEmpty()) {
      addError("totalCost", "Загальна вартість не може бути пустою!");
      return;
    }

    try {
      double totalCost = Double.parseDouble(totalCostStr.trim());
      if (totalCost < 0) {
        addError("totalCost", "Загальна вартість не може бути менше 0!");
      } else {
        this.totalCost = totalCost;
      }
    } catch (NumberFormatException e) {
      addError("totalCost", "Загальна вартість повинна бути числом!");
    }
  }


  @Override
  public String toString() {
    return String.format("Rental: User=%s, Bicycle=%s, Start=%s, End=%s, Total=%.2f",
        userId, bicycleId, start, end, totalCost);
  }
}
