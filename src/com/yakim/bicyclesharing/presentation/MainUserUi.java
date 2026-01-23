package com.yakim.bicyclesharing.presentation;

import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.domain.Impl.Rental;
import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.RentalStatus;
import com.yakim.bicyclesharing.domain.enums.StateBicycle;
import com.yakim.bicyclesharing.services.BicycleService;
import com.yakim.bicyclesharing.services.RentalService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainUserUi {

  private static final BicycleService bicycleService = new BicycleService();
  private static final RentalService rentalService = new RentalService();
  private static final Scanner scarnner = new Scanner(System.in);

  private static List<Bicycle> bicycles;
  private static List<Bicycle> bicyclesAvailable;
  private static List<Rental> userRentals;
  private static List<Rental> activeRentals;
  private static User currentUser;

  public static void userMenu(User user) {
    currentUser = user;
    System.out.println("Меню корисутвача\n");
    while (true) {
      bicycles = bicycleService.getAll();
      bicyclesAvailable = bicycleService.getByState(StateBicycle.AVAILABLE);
      userRentals = rentalService.getByUserId(currentUser.getId());
      activeRentals = rentalService.getByActiveAndUserId(currentUser.getId());

      System.out.println("1 - Подивитися всі велосипеди");
      System.out.println("2 - Подивитися всі доступні велосипеди для оренди");
      System.out.println("3 - Орендувати");
      System.out.println("4 - Відмовитися від оренди");
      System.out.println("5 - Подивитися всі мої оренди");
      System.out.println("0 - Вихід");

      String choice = scarnner.nextLine();

      if (choice.equals("1")) {
        showAllBicycles();
      } else if (choice.equals("2")) {
        showAllAvailableBicycles();
      } else if (choice.equals("3")) {
        arrangeRental();
      } else if (choice.equals("4")) {
        giveBackBicycle();
      } else if (choice.equals("5")) {
        showAllRentals();
      } else if (choice.equals("0")) {
        return;
      }
    }
  }

  private static void showAllBicycles() {
    int count = 1;
    for (var bicycle : bicycles) {
      System.out.println(count + ". " + bicycle);
      count++;
    }
  }

  private static void showAllAvailableBicycles() {
    int count = 1;
    if (bicyclesAvailable == null || bicyclesAvailable.isEmpty()) {
      System.out.println("Упс! Нема жодних доступних велосипедів");
      return;
    }
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
    int choice = choiseUser() - 1;
    if (choice < 0 || choice >= bicyclesAvailable.size()) {
      System.out.println("В списку за даним номером нема такого велосипеда");
    } else {
      Bicycle rented = bicyclesAvailable.get(choice);
      System.out.println("Успішна оренда!");
      Rental currentRental = rentalService.addNewRental(
          new Rental(currentUser.getId(), rented.getId(), UUID.randomUUID()));
      rented.setState(StateBicycle.RENTED);
      rented.setRentalId(currentRental.getId());
      bicycleService.updateBicycle(rented);
    }
  }

  private static void giveBackBicycle() {
    showActiveRentals();
    System.out.println("Виберіть одну з оренд, яку хочете скасувати");
    int choice = choiseUser() - 1;
    if (choice < 0 || choice >= activeRentals.size()) {
      System.out.println("В списку за даним номером нема такої оренди");
    } else {
      Rental currentRental = activeRentals.get(choice);
      Bicycle rented = bicycleService.getById(currentRental.getBicycleId());

      currentRental.setRentalStatus(RentalStatus.INACTIVE);
      currentRental.setEnd(LocalDateTime.now());
      rented.setState(StateBicycle.AVAILABLE);

      Duration duration = Duration.between(currentRental.getStart(), currentRental.getEnd());
      double hours = duration.toMinutes() / 60.0;
      double totalCost = Math.round(hours * rented.getPricePerHour() * 100.0) / 100.0;

      currentRental.setTotalCost(totalCost);
      bicycleService.updateBicycle(rented);
      rentalService.updateRental(currentRental);

      System.out.println("Ви повернули велосипед. Загальна вартість: " + totalCost + " грн");
    }
  }

  private static void showAllRentals() {
    if (userRentals == null || userRentals.isEmpty()) {
      System.out.println("У вас нема жодних оренд");
      return;
    }
    int count = 1;
    for (var rental : userRentals) {
      System.out.println(count + ". " + rental);
      count++;
    }
  }

  private static void showActiveRentals() {
    if (activeRentals == null || activeRentals.isEmpty()) {
      System.out.println("У вас нема активних оренд");
      return;
    }
    int count = 1;
    for (var rental : activeRentals) {
      System.out.println(count + ". " + rental);
      count++;
    }
  }

  private static int choiseUser() {
    while (true) {
      String input = scarnner.nextLine();
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Упс, ви ввели не число! Спробуйте ще раз:");
      }
    }
  }
}
