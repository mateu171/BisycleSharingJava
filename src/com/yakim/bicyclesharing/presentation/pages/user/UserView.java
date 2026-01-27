package com.yakim.bicyclesharing.presentation.pages.user;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.presentation.enums.EntityName;
import com.yakim.bicyclesharing.presentation.forms.user.RentBicycleForm;
import com.yakim.bicyclesharing.presentation.forms.user.ReturnBicycleForm;
import com.yakim.bicyclesharing.services.BicycleService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import java.util.Scanner;

public class UserView {

  private final User currentUser;
  private final Scanner scanner = new Scanner(System.in);
  private final BicycleService bicycleService = AppConfig.bicycleService();

  public UserView(User user) {
    this.currentUser = user;
  }

  public void show() {
    while (true) {
      System.out.println("МЕНЮ КОРИСТУВАЧА");
      System.out.println("1 - Всі велосипеди");
      System.out.println("2 - Доступні велосипеди");
      System.out.println("3 - Орендувати велосипед");
      System.out.println("4 - Повернути велосипед");
      System.out.println("5 - Мої оренди");
      System.out.println("0 - Вийти");

      String choice = scanner.nextLine();

      switch (choice) {
        case "1" -> ConsoleHelper.showAll(bicycleService.getAll(), EntityName.BICYCLES);
        case "2" -> new AvailableBicyclesView().showAllAvailableBicycles();
        case "3" -> new RentBicycleForm(currentUser).arrangeRental();
        case "4" -> new ReturnBicycleForm(currentUser).giveBackBicycle();
        case "5" -> new MyRentalsView(currentUser).showActiveRentals();
        case "0" -> {
          return;
        }
        default -> System.out.println("Невірний вибір!");
      }
    }
  }
}
