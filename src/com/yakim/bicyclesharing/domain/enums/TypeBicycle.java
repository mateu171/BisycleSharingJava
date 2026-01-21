package com.yakim.bicyclesharing.domain.enums;

public enum TypeBicycle {
  MOUNTAIN("гірський"), HIGHWAY("шосейний"), URBAN("міський");

  private final Object name;

  TypeBicycle(String name) {
    this.name = name;
  }

  public Object getName() {
    return name;
  }
}
