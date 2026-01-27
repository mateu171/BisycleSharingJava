package com.yakim.bicyclesharing.presentation.forms.admin.user;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.services.UserService;
import com.yakim.bicyclesharing.util.AppConfig;
import java.util.Scanner;

public class SearchUserForm {

  private final Scanner scanner = new Scanner(System.in);
  private final UserService userService = AppConfig.userService();


  public void searchUser() {
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
