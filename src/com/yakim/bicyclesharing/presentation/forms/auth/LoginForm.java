package com.yakim.bicyclesharing.presentation.forms.auth;

import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.Role;
import com.yakim.bicyclesharing.exeption.AuthException;
import com.yakim.bicyclesharing.presentation.pages.admin.AdminView;
import com.yakim.bicyclesharing.presentation.pages.user.UserView;
import com.yakim.bicyclesharing.services.AuthService;
import com.yakim.bicyclesharing.util.AppConfig;
import java.util.Scanner;

public class LoginForm {

  private final AuthService authService = AppConfig.authService();
  private final Scanner scanner = new Scanner(System.in);

  public void login() {
    System.out.println("Авторизація\n");

    System.out.println("Введіть свій логін");
    String login = scanner.nextLine();
    System.out.println("Введіть свій пароль");
    String password = scanner.nextLine();

    try {
      User currentUser = authService.authenticate(login, password);
      if (currentUser.getRole() == Role.CLIENT) {
        new UserView(currentUser).show();
      } else if (currentUser.getRole() == Role.ADMIN) {
        new AdminView().show();
      }
    } catch (AuthException exception) {
      System.out.println(exception.getMessage());
    }
  }
}
