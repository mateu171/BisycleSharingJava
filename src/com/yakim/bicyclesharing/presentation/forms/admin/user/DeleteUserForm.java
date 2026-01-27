package com.yakim.bicyclesharing.presentation.forms.admin.user;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.presentation.enums.EntityName;
import com.yakim.bicyclesharing.services.UserService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import java.util.List;

public class DeleteUserForm {

  private final UserService userService = AppConfig.userService();
  private List<User> users;

  public void deleteUser() {
    users = userService.getAll();

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
}
