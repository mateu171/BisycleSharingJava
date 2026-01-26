package com.yakim.bicyclesharing.presentation.main;

import com.yakim.bicyclesharing.presentation.menus.BicycleMenu;
import com.yakim.bicyclesharing.presentation.menus.StationMenu;
import com.yakim.bicyclesharing.presentation.menus.UserMenu;
import com.yakim.bicyclesharing.services.BicycleService;
import com.yakim.bicyclesharing.services.EmployeeService;
import com.yakim.bicyclesharing.services.StationService;
import com.yakim.bicyclesharing.services.UserService;
import com.yakim.bicyclesharing.util.AppConfig;
import java.util.Scanner;

public class MainAdminUi {

  private static final Scanner scanner = new Scanner(System.in);

  private static final UserService userService = AppConfig.userService();
  private static final BicycleService bicycleService = AppConfig.bicycleService();
  private static final StationService stationService = AppConfig.stationService();
  private static final EmployeeService employeeService = AppConfig.employeeService();

  private static final UserMenu userMenu = new UserMenu(userService);
  private static final StationMenu stationMenu = new StationMenu(stationService, employeeService);
  private static final BicycleMenu bicycleMenu = new BicycleMenu(bicycleService);

  public static void adminMenu() {
    while (true) {

      System.out.println("АДМІН МЕНЮ");
      System.out.println("1 - Користувачі");
      System.out.println("2 - Станції");
      System.out.println("3 - Велосипеди");
      System.out.println("0 - Вийти");
      System.out.print("Виберіть опцію: ");

      String choice = scanner.nextLine();

      switch (choice) {
        case "1" -> userMenu.userSubMenu();
        case "2" -> stationMenu.stationSubMenu();
        case "3" -> bicycleMenu.bicycleMenu();
        case "0" -> {
          System.out.println("Вихід з адмін меню");
          return;
        }
        default -> System.out.println("Невірний вибір!");
      }
      System.out.println();
    }
  }

}
