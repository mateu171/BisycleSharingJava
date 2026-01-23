package com.yakim.bicyclesharing.util;

public enum EntityName {
  USERS("користучів"), BICYCLES("велосипедів"), STATIONS("станції"), EMPLOYEES("робітників");

  private final String name;

  EntityName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
