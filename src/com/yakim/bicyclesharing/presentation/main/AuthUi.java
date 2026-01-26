package com.yakim.bicyclesharing.presentation.main;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.Role;
import com.yakim.bicyclesharing.exeption.AuthException;
import com.yakim.bicyclesharing.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.exeption.EmailExeption;
import com.yakim.bicyclesharing.services.AuthService;
import com.yakim.bicyclesharing.services.UserService;
import com.yakim.bicyclesharing.services.VerificationService;
import com.yakim.bicyclesharing.util.AppConfig;
import java.util.Scanner;

public class AuthUi {

  private static final UserService userService = AppConfig.userService();
  private static final AuthService authService = AppConfig.authService();
  private static final VerificationService verifi = AppConfig.verificationService();
  private static final Scanner scanner = new Scanner(System.in);

  public static void startUp() {
    System.out.println("Ласкаво просимо до додатку BicycleSharing");

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
    System.out.println("Авторизація\n");

    System.out.println("Введіть свій логін");
    String login = scanner.nextLine();
    System.out.println("Введіть свій пароль");
    String password = scanner.nextLine();

    try {
      User currentUser = authService.authenticate(login, password);
      if (currentUser.getRole() == Role.CLIENT) {
        MainUserUi.mainUserMenu(currentUser);
      } else if (currentUser.getRole() == Role.ADMIN) {
        MainAdminUi.adminMenu();
      }
    } catch (AuthException exception) {
      System.out.println(exception.getMessage());
    }
  }

  private static void register() {
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

        if (userService.existsByLogin(user.getLogin())) {
          System.out.println("Упс! Схоже що даний логін занятий\nСпробуйте ще раз\n");
        } else {
          try {
            int code = verifi.sendVerificationCode(email);
            System.out.println("Введіть код, який прийшов на email:");
            String userCode = scanner.nextLine();

            if (!Integer.toString(code).equals(userCode)) {
              System.out.println("Неправильний код, реєстрація скасована");
              return;
            }

            System.out.println("Успішна реєстрація");
            userService.add(user);
            return;
          } catch (EmailExeption e) {
            System.out.println(e.getMessage());
            System.out.println("Спробуйте знову\n");
          }
        }
      } catch (CustomEntityValidationExeption exception) {
        System.out.println(exception.getMessage());
      }
    }

  }


}
