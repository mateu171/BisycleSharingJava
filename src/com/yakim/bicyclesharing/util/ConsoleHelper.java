package com.yakim.bicyclesharing.util;

import java.util.List;
import java.util.Scanner;

public class ConsoleHelper {

  private static final Scanner scanner = new Scanner(System.in);

  public static void showAll(List<? extends BaseEntity> entities, EntityName entityName) {
    if (entities == null || entities.isEmpty()) {
      System.out.println("Список " + entityName.getName() + " пустий!");
      return;
    }
    int count = 1;
    for (var entity : entities) {
      System.out.println(count + ". " + entity);
      count++;
    }
  }

  public static int chooseUser() {
    while (true) {
      String input = scanner.nextLine();
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Упс, ви ввели не число! Спробуйте ще раз:");
      }
    }
  }
}
