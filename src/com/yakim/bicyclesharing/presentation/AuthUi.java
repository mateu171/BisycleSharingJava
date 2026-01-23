package com.yakim.bicyclesharing.presentation;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.Role;
import com.yakim.bicyclesharing.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.services.UserService;
import java.util.Scanner;

public class AuthUi {

  private static final UserService userService = new UserService();

  public static void startUp() {
    System.out.println("Ласкаво просимо до додатку BicycleSharing");

    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println();
      System.out.println("Виберіть одну з доступних дій");
      System.out.println();
      System.out.println(" 1 - Вхід\n 2 - Реєстрація\n 3 - Вихід");
      String choise = scanner.nextLine();

      if (choise.equals("1")) {
        login();
      } else if (choise.equals("2")) {
        register();
      } else if (choise.equals("3")) {
        return;
      } else {
        System.out.println("Помилка, такої опції нема\nСпробуйте ще раз!");
      }
    }
  }


  private static void login() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Авторизація\n");

    System.out.println("Введіть свій логін");
    String login = scanner.nextLine();
    System.out.println("Введіть свій пароль");
    String password = scanner.nextLine();

    User curretnUser = userService.getByLogin(login);

    if (curretnUser != null && curretnUser.getPassword().equals(password)) {
      System.out.println("Успішна авторизація");
      if (curretnUser.getRole() == Role.CLIENT) {
        MainUserUi.userMenu(curretnUser);
      } else if (curretnUser.getRole() == Role.ADMIN) {
        MainAdminUi.adminMenu();
      }
    } else {
      System.out.println("Помилка валідації");
    }
  }

  private static void register() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Реєстрація\n");

    while (true) {
      System.out.println("Створіть свій логін");
      String login = scanner.nextLine();
      System.out.println("Створіть свій пароль");
      String password = scanner.nextLine();
      System.out.println("Створіть свій email");
      String email = scanner.nextLine();

      try {
        User user = new User(login, password, email, Role.CLIENT);

        if (userService.getByLogin(user.getLogin()) != null) {
          System.out.println("Упс! Схоже що даний логін занятий\nСпробуйте ще раз\n");
        } else {
          System.out.println("Успішна реєстрація");
          userService.addNewUser(user);
          return;
        }
      } catch (CustomEntityValidationExeption exeption) {
        System.out.println(exeption.getMessage());
      }
    }

  }


}
