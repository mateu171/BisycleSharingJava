package com.yakim.bicyclesharing.presentation.forms.auth;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.Role;
import com.yakim.bicyclesharing.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.exeption.EmailExeption;
import com.yakim.bicyclesharing.services.UserService;
import com.yakim.bicyclesharing.services.VerificationService;
import com.yakim.bicyclesharing.util.AppConfig;
import java.util.Scanner;

public class SignUpForm {

  private final UserService userService = AppConfig.userService();
  private final VerificationService verificationService = AppConfig.verificationService();
  private final Scanner scanner = new Scanner(System.in);

  public void register() {
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
            int code = verificationService.sendVerificationCode(email);
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
