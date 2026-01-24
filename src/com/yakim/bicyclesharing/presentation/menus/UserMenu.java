package com.yakim.bicyclesharing.presentation.menus;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.Role;
import com.yakim.bicyclesharing.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.services.UserService;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import com.yakim.bicyclesharing.util.EntityName;
import java.util.List;
import java.util.Scanner;

public class UserMenu {

  private final Scanner scanner = new Scanner(System.in);
  private final UserService userService;
  private List<User> users;

  public UserMenu(UserService userService) {
    this.userService = userService;
  }

  public void userSubMenu() {
    while (true) {
      users = userService.getAll();
      System.out.println("--- Користувачі ---");
      System.out.println("1 - Переглянути всіх користувачів");
      System.out.println("2 - Додати користувача");
      System.out.println("3 - Редагувати користувача");
      System.out.println("4 - Видалити користувача");
      System.out.println("5 - Пошук користувача");
      System.out.println("0 - Назад");
      System.out.print("Виберіть опцію: ");
      String choice = scanner.nextLine();
      switch (choice) {
        case "1" -> ConsoleHelper.showAll(users, EntityName.USERS);
        case "2" -> addNewUser();
        case "3" -> editUser();
        case "4" -> deleteUser();
        case "5" -> searchUser();
        case "0" -> {
          return;
        }
        default -> System.out.println("Невірний вибір!");
      }
    }
  }

  private void addNewUser() {
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

  private void editUser() {
    ConsoleHelper.showAll(users, EntityName.USERS);
    System.out.println();
    System.out.println("Виберіть одного з користувачів для редагування");
    int index = ConsoleHelper.chooseUser() - 1;
    if (index < 0 || index >= users.size()) {
      System.out.println("Невірний вибір користувача!");
      return;
    }

    User currentUser = users.get(index);

    System.out.println("Редагування користувача: " + currentUser.getLogin());

    String newLogin;
    while (true) {
      System.out.print("Новий логін (обов'язково, поточний: " + currentUser.getLogin() + "): ");
      newLogin = scanner.nextLine();
      if (!newLogin.isBlank()) {
        break;
      } else {
        System.out.println("Логін обов'язковий!");
      }
    }

    System.out.print(
        "Новий email (Enter щоб пропустити, поточний: " + currentUser.getEmail() + "): ");
    String newEmail = scanner.nextLine();
    if (!newEmail.isBlank()) {
      currentUser.setEmail(newEmail);
    }

    System.out.print("Новий пароль (Enter щоб пропустити): ");
    String newPassword = scanner.nextLine();
    if (!newPassword.isBlank()) {
      currentUser.setPassword(newPassword);
    }
    try {
      User tempUser = new User(newLogin, currentUser.getPassword(), currentUser.getEmail(),
          currentUser.getRole());
      if (userService.existsByLogin(newLogin)) {
        System.out.println("Даний логін вже занятий");
        return;
      }
      System.out.println("Користувача успішно оновлено: " + currentUser.getLogin());
      currentUser.setLogin(tempUser.getLogin());
      currentUser.setEmail(tempUser.getEmail());
      currentUser.setPassword(tempUser.getPassword());

      userService.update(currentUser);
    } catch (CustomEntityValidationExeption e) {
      System.out.println(e.getMessage());
    }
  }

  private void deleteUser() {
    ConsoleHelper.showAll(users, EntityName.USERS);
    System.out.println();
    System.out.println("Виберіть одного з користувачів для видалення");
    int index = ConsoleHelper.chooseUser() - 1;
    if (index < 0 || index >= users.size()) {
      System.out.println("Невірний вибір користувача!");
      return;
    }

    User currentUser = users.get(index);

    if (userService.delete(currentUser)) {
      System.out.println("Успішне видалення");

    } else {
      System.out.println("Невдалося видалити");
    }
  }

  private void searchUser() {
    System.out.println("Введіть логін корисутвача, якого хочете знайти: ");
    String searchLogin = scanner.nextLine();
    User user = userService.getByLogin(searchLogin);
    if (user != null) {
      System.out.println("Успішний пошук");
      System.out.println(user);
    } else {
      System.out.println("Невдалося знайти користувача");
    }
  }

}
