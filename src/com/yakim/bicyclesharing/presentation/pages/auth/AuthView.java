package com.yakim.bicyclesharing.presentation.pages.auth;

import com.yakim.bicyclesharing.presentation.forms.auth.LoginForm;
import com.yakim.bicyclesharing.presentation.forms.auth.SignUpForm;
import java.util.Scanner;

public class AuthView {

  Scanner scanner = new Scanner(System.in);

  public void startUp() {
    System.out.println("Ласкаво просимо до додатку BicycleSharing");

    while (true) {
      System.out.println();
      System.out.println("Виберіть одну з доступних дій");
      System.out.println();
      System.out.println(" 1 - Вхід\n 2 - Реєстрація\n 3 - Вихід");
      String choise = scanner.nextLine();

      if (choise.equals("1")) {
        new LoginForm().login();
      } else if (choise.equals("2")) {
        new SignUpForm().register();
      } else if (choise.equals("3")) {
        return;
      } else {
        System.out.println("Помилка, такої опції нема\nСпробуйте ще раз!");
      }
    }
  }
}
