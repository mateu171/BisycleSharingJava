package com.yakim.bicyclesharing.presentation.pages.admin;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.presentation.enums.EntityName;
import com.yakim.bicyclesharing.presentation.forms.admin.user.AddUserForm;
import com.yakim.bicyclesharing.presentation.forms.admin.user.DeleteUserForm;
import com.yakim.bicyclesharing.presentation.forms.admin.user.EditUserForm;
import com.yakim.bicyclesharing.presentation.forms.admin.user.SearchUserForm;
import com.yakim.bicyclesharing.services.UserService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import java.util.List;
import java.util.Scanner;

public class UserView {

  private final UserService userService = AppConfig.userService();
  private final Scanner scanner = new Scanner(System.in);
  private List<User> users;

  public void userSubMenu() {
    while (true) {
      users = userService.getAll();
      System.out.println("Користувачі");
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
        case "2" -> new AddUserForm().addNewUser();
        case "3" -> new EditUserForm().editUser();
        case "4" -> new DeleteUserForm().deleteUser();
        case "5" -> new SearchUserForm().searchUser();
        case "0" -> {
          return;
        }
        default -> System.out.println("Невірний вибір!");
      }
    }
  }
}
