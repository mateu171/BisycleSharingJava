package com.yakim.bicyclesharing.presentation.forms.admin.bicycle;

import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.domain.enums.TypeBicycle;
import com.yakim.bicyclesharing.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.services.BicycleService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import java.util.Scanner;

public class AddBicycleForm {

  private final Scanner scanner = new Scanner(System.in);
  private final BicycleService bicycleService = AppConfig.bicycleService();

  public void addNewBicycle() {
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
}
