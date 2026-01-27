package com.yakim.bicyclesharing.presentation.pages.admin;

import com.yakim.bicyclesharing.presentation.enums.EntityName;
import com.yakim.bicyclesharing.presentation.forms.admin.bicycle.AddBicycleForm;
import com.yakim.bicyclesharing.presentation.forms.admin.bicycle.DeleteBicycleForm;
import com.yakim.bicyclesharing.presentation.forms.admin.bicycle.EditBicycleForm;
import com.yakim.bicyclesharing.services.BicycleService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import java.util.Scanner;

public class BicycleView {

  private final BicycleService bicycleService = AppConfig.bicycleService();
  private final Scanner scanner = new Scanner(System.in);

  public void show() {
    while (true) {
      System.out.println("ВЕЛОСИПЕДИ");
      System.out.println("1 - Переглянути");
      System.out.println("2 - Додати");
      System.out.println("3 - Редагувати");
      System.out.println("4 - Видалити");
      System.out.println("0 - Назад");

      String choice = scanner.nextLine();

      switch (choice) {
        case "1" -> ConsoleHelper.showAll(bicycleService.getAll(), EntityName.BICYCLES);
        case "2" -> new AddBicycleForm().addNewBicycle();
        case "3" -> new EditBicycleForm().editBicycle();
        case "4" -> new DeleteBicycleForm().deleteBicycle();
        case "0" -> {
          return;
        }
        default -> System.out.println("Невірний вибір!");
      }
    }
  }
}
