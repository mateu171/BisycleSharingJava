package com.yakim.bicyclesharing.domain.Impl;

import com.yakim.bicyclesharing.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.util.BaseEntity;
import java.util.List;
import java.util.UUID;

public class Station extends BaseEntity {

  private String name;
  private String address;
  private int countPlaces;
  private List<UUID> employeesId;

  private Station() {
    super();
  }

  public Station(String name, String address, String countPlaces, List<UUID> employeesId) {
    this();
    setName(name);
    setAddress(address);
    setCountPlaces(countPlaces);
    setEmployeesId(employeesId);

    if (!isValid()) {
      throw new CustomEntityValidationExeption(getErrors());
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    cleanErrors("name");
    if (name == null || name.trim().isEmpty()) {
      addError("name", "Назва станції не може бути пустою!");
    } else if (name.length() < 2 || name.length() > 50) {
      addError("name", "Назва станції повинна бути від 2 до 50 символів!");
    }
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    cleanErrors("address");
    if (address == null || address.trim().isEmpty()) {
      addError("address", "Адреса не може бути пустою!");
    } else if (address.length() < 5 || address.length() > 100) {
      addError("address", "Адреса повинна бути від 5 до 100 символів!");
    }
    this.address = address;
  }

  public int getCountPlaces() {
    return countPlaces;
  }

  public void setCountPlaces(String countPlacesStr) {
    cleanErrors("countPlaces");

    if (countPlacesStr == null || countPlacesStr.trim().isEmpty()) {
      addError("countPlaces", "Кількість місць не може бути пустою!");
      return;
    }

    try {
      int countPlaces = Integer.parseInt(countPlacesStr.trim());
      if (countPlaces < 0) {
        addError("countPlaces", "Кількість місць не може бути від’ємною!");
      } else {
        this.countPlaces = countPlaces;
      }
    } catch (NumberFormatException e) {
      addError("countPlaces", "Кількість місць повинна бути цілим числом!");
    }
  }


  public List<UUID> getEmployeesId() {
    return employeesId;
  }

  public void setEmployeesId(List<UUID> employeesId) {
    cleanErrors("employeesId");
    if (employeesId == null) {
      addError("employeesId", "Список працівників не може бути null!");
    }
    this.employeesId = employeesId;
  }

  @Override
  public String toString() {
    return String.format("Station: %s, Address: %s, Places: %d, Employees: %d",
        name, address, countPlaces, employeesId == null ? 0 : employeesId.size());
  }
}
