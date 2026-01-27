package com.yakim.bicyclesharing.presentation.pages.admin;

import java.util.Scanner;

public class AdminView {

  private final Scanner scanner = new Scanner(System.in);

  public void show() {
    while (true) {
      System.out.println("АДМІН МЕНЮ");
      System.out.println("1 - Користувачі");
      System.out.println("2 - Станції");
      System.out.println("3 - Велосипеди");
      System.out.println("0 - Вийти");

      String choice = scanner.nextLine();

      switch (choice) {
        case "1" -> new UserView().userSubMenu();
        case "2" -> new StationView().show();
        case "3" -> new BicycleView().show();
        case "0" -> {
          return;
        }
        default -> System.out.println("Невірний вибір!");
      }
    }
  }
}
