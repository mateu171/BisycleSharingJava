package com.yakim.bicyclesharing.presentation.menus;

import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.domain.enums.TypeBicycle;
import com.yakim.bicyclesharing.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.services.BicycleService;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import com.yakim.bicyclesharing.util.EntityName;
import java.util.List;
import java.util.Scanner;

public class BicycleMenu {

  private final Scanner scanner = new Scanner(System.in);
  private final BicycleService bicycleService;
  private List<Bicycle> bicycles;

  public BicycleMenu(BicycleService bicycleService) {
    this.bicycleService = bicycleService;
  }

  public void bicycleMenu() {
    while (true) {
      bicycles = bicycleService.getAll();

      System.out.println("Меню керування велосипедами:");
      System.out.println("1 - Переглянути всі велосипеди");
      System.out.println("2 - Додати новий велосипед");
      System.out.println("3 - Редагувати велосипед");
      System.out.println("4 - Видалити велосипед");
      System.out.println("0 - Повернутись в адмін меню");
      System.out.print("Виберіть дію: ");

      String choice = scanner.nextLine();

      switch (choice) {
        case "1" -> ConsoleHelper.showAll(bicycles, EntityName.BICYCLES);
        case "2" -> addNewBicycle();
        case "3" -> editBicycle();
        case "4" -> deleteBicycle();
        case "0" -> {
          return;
        }
        default -> System.out.println("Невірний вибір!");
      }

      System.out.println();
    }
  }

  private void addNewBicycle() {
    System.out.print("Введіть модель велосипеда: ");
    String model = scanner.nextLine();

    System.out.println("Виберіть тип велосипеда:");
    for (int i = 0; i < TypeBicycle.values().length; i++) {
      System.out.println((i + 1) + " - " + TypeBicycle.values()[i].getName());
    }
    int typeIndex = ConsoleHelper.chooseUser() - 1;

    if (typeIndex < 0 || typeIndex >= TypeBicycle.values().length) {
      System.out.println("Невірний вибір");
      return;
    }
    TypeBicycle type = TypeBicycle.values()[typeIndex];

    System.out.print("Введіть ціну за годину: ");
    String price = scanner.nextLine();

    Bicycle bicycle;
    try {
      bicycle = new Bicycle(model, type, price);
      bicycleService.add(bicycle);
      System.out.println("Велосипед успішно додано!");
    } catch (CustomEntityValidationExeption e) {
      System.out.println(e.getMessage());
    }
  }

  private void editBicycle() {
    ConsoleHelper.showAll(bicycles, EntityName.BICYCLES);
    System.out.println();
    System.out.println("Виберіть велосипед для редагування:");
    int index = ConsoleHelper.chooseUser() - 1;
    if (index < 0 || index >= bicycles.size()) {
      System.out.println("Невірний вибір!");
      return;
    }

    Bicycle currentBicycle = bicycles.get(index);
    System.out.println("Редагування велосипеда: " + currentBicycle.getModel());

    String newModel;
    while (true) {
      System.out.print("Нова модель (обов'язково, поточна: " + currentBicycle.getModel() + "): ");
      newModel = scanner.nextLine();
      if (!newModel.isBlank()) {
        break;
      } else {
        System.out.println("Модель обов'язкова!");
      }
    }

    System.out.println(
        "Виберіть новий тип (Enter щоб пропустити, поточний: " + currentBicycle.getTypeBicycle()
            .getName() + "):");
    for (int i = 0; i < TypeBicycle.values().length; i++) {
      System.out.println((i + 1) + " - " + TypeBicycle.values()[i].getName());
    }
    String typeInput = scanner.nextLine();
    TypeBicycle newType = currentBicycle.getTypeBicycle();
    if (!typeInput.isBlank()) {
      try {
        int typeIndex = Integer.parseInt(typeInput) - 1;
        if (typeIndex >= 0 && typeIndex < TypeBicycle.values().length) {
          newType = TypeBicycle.values()[typeIndex];
        } else {
          System.out.println("Невірний вибір типу, залишено попередній.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Некоректне число, залишено попередній тип.");
      }
    }

    System.out.print(
        "Нова ціна за годину (Enter щоб пропустити, поточна: " + currentBicycle.getPricePerHour()
            + "): ");
    String newPriceStr = scanner.nextLine();
    double newPrice = currentBicycle.getPricePerHour();
    if (!newPriceStr.isBlank()) {
      try {
        newPrice = Double.parseDouble(newPriceStr);
      } catch (NumberFormatException e) {
        System.out.println("Некоректна ціна, залишено попередню.");
      }
    }

    try {
      Bicycle tempBicycle = new Bicycle(newModel, newType, String.valueOf(newPrice));
      currentBicycle.setModel(tempBicycle.getModel());
      currentBicycle.setTypeBicycle(tempBicycle.getTypeBicycle());
      currentBicycle.setPricePerHour(String.valueOf(tempBicycle.getPricePerHour()));

      bicycleService.update(currentBicycle);
      System.out.println("Велосипед успішно оновлено: " + currentBicycle.getModel());
    } catch (CustomEntityValidationExeption e) {
      System.out.println("Помилки при оновленні велосипеда:");
      System.out.println(e.getMessage());
    }
  }

  private void deleteBicycle() {
    ConsoleHelper.showAll(bicycles, EntityName.BICYCLES);
    System.out.println("Виберіть велосипед для видалення:");
    int index = ConsoleHelper.chooseUser() - 1;
    if (index < 0 || index >= bicycles.size()) {
      System.out.println("Невірний вибір!");
      return;
    }

    Bicycle bicycle = bicycles.get(index);
    if (bicycleService.delete(bicycle)) {
      System.out.println("Велосипед видалено!");
    } else {
      System.out.println("Невдалося видалити");
    }
  }


}
