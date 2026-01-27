package com.yakim.bicyclesharing.presentation.forms.admin.user;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.Role;
import com.yakim.bicyclesharing.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.services.UserService;
import com.yakim.bicyclesharing.util.AppConfig;
import java.util.Scanner;

public class AddUserForm {

  private final Scanner scanner = new Scanner(System.in);
  private final UserService userService = AppConfig.userService();

  public void addNewUser() {
    System.out.print("Введіть логін: ");
    String login = scanner.nextLine();

    System.out.print("Введіть пароль: ");
    String password = scanner.nextLine();

    System.out.print("Введіть email: ");
    String email = scanner.nextLine();

    System.out.println("Виберіть роль для нового користувача:");
    Role[] roles = Role.values();
    for (int i = 0; i < roles.length; i++) {
      System.out.println((i + 1) + " - " + roles[i].getName());
    }
    System.out.print("Ваш вибір: ");
    int roleChoice;
    try {
      roleChoice = Integer.parseInt(scanner.nextLine()) - 1;
      if (roleChoice < 0 || roleChoice >= roles.length) {
        System.out.println("Невірний вибір ролі!");
        return;
      }
    } catch (NumberFormatException e) {
      System.out.println("Невірний ввід!");
      return;
    }

    Role selectedRole = roles[roleChoice];
    User newUser;

    try {
      newUser = new User(login, password, email, selectedRole);

      if (userService.existsByLogin(newUser.getLogin())) {
        System.out.println("Упс! Схоже що даний логін занятий\nСпробуйте ще раз\n");
      } else {
        System.out.println(
            "Користувача додано! Логін: " + login + ", Роль: " + selectedRole.getName());
        userService.add(newUser);
      }
    } catch (CustomEntityValidationExeption exeption) {
      System.out.println(exeption.getMessage());
    }

  }
}
