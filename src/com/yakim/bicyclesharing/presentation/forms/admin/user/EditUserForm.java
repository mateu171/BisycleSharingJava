package com.yakim.bicyclesharing.presentation.forms.admin.user;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.presentation.enums.EntityName;
import com.yakim.bicyclesharing.services.UserService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import java.util.List;
import java.util.Scanner;

public class EditUserForm {

  private final Scanner scanner = new Scanner(System.in);
  private final UserService userService = AppConfig.userService();
  private List<User> users;

  public void editUser() {
    users = userService.getAll();

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
}
