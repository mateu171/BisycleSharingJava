package com.yakim.bicyclesharing.presentation.main;

import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.domain.Impl.Rental;
import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.StateBicycle;
import com.yakim.bicyclesharing.services.BicycleService;
import com.yakim.bicyclesharing.services.RentalService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import com.yakim.bicyclesharing.util.EntityName;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainUserUi {

  private static final BicycleService bicycleService = AppConfig.bicycleService();
  private static final RentalService rentalService = AppConfig.rentalService();
  private static final Scanner scanner = new Scanner(System.in);

  private static List<Bicycle> bicycles;
  private static List<Bicycle> bicyclesAvailable;
  private static List<Rental> activeRentals;
  private static User currentUser;

  public static void mainUserMenu(User user) {
    currentUser = user;
    System.out.println("Меню корисутвача\n");
    while (true) {
      bicycles = bicycleService.getAll();
      bicyclesAvailable = bicycleService.getByState(StateBicycle.AVAILABLE);
      activeRentals = rentalService.getByActiveAndUserId(currentUser.getId());

      System.out.println("1 - Подивитися всі велосипеди");
      System.out.println("2 - Подивитися всі доступні велосипеди для оренди");
      System.out.println("3 - Орендувати");
      System.out.println("4 - Відмовитися від оренди");
      System.out.println("5 - Подивитися всі мої оренди");
      System.out.println("0 - Вихід");

      String choice = scanner.nextLine();

      if (choice.equals("1")) {
        ConsoleHelper.showAll(bicycles, EntityName.BICYCLES);
      } else if (choice.equals("2")) {
        showAllAvailableBicycles();
      } else if (choice.equals("3")) {
        arrangeRental();
      } else if (choice.equals("4")) {
        giveBackBicycle();
      } else if (choice.equals("5")) {
        showActiveRentals();
      } else if (choice.equals("0")) {
        return;
      }
    }
  }

  private static void showAllAvailableBicycles() {
    int count = 1;
    System.out.println("Список всіх доступних велосипедів для оренди");
    for (var bicycle : bicyclesAvailable) {
      System.out.println(count + ". " + bicycle);
      count++;
    }
  }

  private static void arrangeRental() {
    if (bicyclesAvailable.isEmpty()) {
      System.out.println("Нема доступних велосипедів для оренди");
      return;
    }
    showAllAvailableBicycles();
    System.out.println();
    System.out.println("Виберіть велосипед для оренди");
    int choice = ConsoleHelper.chooseUser() - 1;
    if (choice < 0 || choice >= bicyclesAvailable.size()) {
      System.out.println("В списку за даним номером нема такого велосипеда");
    } else {
      Bicycle rented = bicyclesAvailable.get(choice);
      System.out.println("Успішна оренда!");
      Rental currentRental = rentalService.add(
          new Rental(currentUser.getId(), rented.getId(), UUID.randomUUID()));
      rented.setState(StateBicycle.RENTED);
      rented.setRentalId(currentRental.getId());
      bicycleService.update(rented);
    }
  }

  private static void giveBackBicycle() {
    if (activeRentals.isEmpty()) {
      System.out.println("У вас нема активних оренд");
      return;
    }
    showActiveRentals();
    System.out.println("Виберіть одну з оренд, яку хочете скасувати");
    int choice = ConsoleHelper.chooseUser() - 1;
    if (choice < 0 || choice >= activeRentals.size()) {
      System.out.println("В списку за даним номером нема такої оренди");
    } else {
      Rental currentRental = activeRentals.get(choice);

      currentRental = rentalService.finishRental(currentRental);

      System.out.println(
          "Ви повернули велосипед. Загальна вартість: " + currentRental.getTotalCost() + " грн");
    }
  }

  private static void showActiveRentals() {
    int count = 1;
    for (var rental : activeRentals) {
      System.out.println(count + ". " + rental);
      count++;
    }
  }
}
