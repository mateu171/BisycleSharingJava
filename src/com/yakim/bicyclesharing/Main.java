package com.yakim.bicyclesharing;

import com.yakim.bicyclesharing.presentation.AuthUi;

public class Main {

  public static void main(String[] args) {
    //MainUserUi.UserMenu(new User());
    AuthUi.startUp();
//    BicycleRepository bicycleRepository =
//        new JsonBicycleRepository("data/bicycles.json");
//
//    BicycleService bicycleService =
//        new BicycleService(bicycleRepository);
//
//    bicycleService.addNewBicycle(
//        new Bicycle("бмік", TypeBicycle.HIGHWAY, "100", UUID.randomUUID()));
  }
}
