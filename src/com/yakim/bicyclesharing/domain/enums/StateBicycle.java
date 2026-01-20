package com.yakim.bicyclesharing.domain.enums;

public enum StateBicycle {
  MAINTENANCE("вільний"), RENTED("в оренді");
  private final Object name;

  StateBicycle(String name) {
    this.name = name;
  }

  public Object getName() {
    return name;
  }
}
