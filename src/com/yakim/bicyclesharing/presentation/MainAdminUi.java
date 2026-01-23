package com.yakim.bicyclesharing.presentation;

import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.domain.Impl.Employee;
import com.yakim.bicyclesharing.domain.Impl.Station;
import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.Role;
import com.yakim.bicyclesharing.domain.enums.TypeBicycle;
import com.yakim.bicyclesharing.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.services.BicycleService;
import com.yakim.bicyclesharing.services.EmployeeService;
import com.yakim.bicyclesharing.services.StationService;
import com.yakim.bicyclesharing.services.UserService;
import com.yakim.bicyclesharing.util.BaseEntity;
import com.yakim.bicyclesharing.util.EntityName;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainAdminUi {

  private static final Scanner scanner = new Scanner(System.in);
  private static final UserService userService = new UserService();
  private static final BicycleService bicycleService = new BicycleService();
  private static final StationService stationService = new StationService();
  private static final EmployeeService employeeService = new EmployeeService();
  private static List<Employee> employees = new ArrayList<>();
  private static List<User> users = new ArrayList<>();
  private static List<Station> stations = new ArrayList<>();
  private static List<Bicycle> bicycles = new ArrayList<>();

  public static void adminMenu() {
    while (true) {
      employees = employeeService.getAll();

      System.out.println("=== АДМІН МЕНЮ ===");
      System.out.println("1 - Користувачі");
      System.out.println("2 - Станції");
      System.out.println("3 - Велосипеди");
      System.out.println("0 - Вийти");
      System.out.print("Виберіть опцію: ");

      String choice = scanner.nextLine();

      switch (choice) {
        case "1" -> userSubMenu();
        case "2" -> stationSubMenu();
        case "3" -> bicycleSubMenu();
        case "0" -> {
          System.out.println("Вихід з адмін меню");
          return;
        }
        default -> System.out.println("Невірний вибір!");
      }

      System.out.println();
    }
  }

  private static void userSubMenu() {
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
        case "1" -> showAllSomething(users, EntityName.USERS);
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

  private static void stationSubMenu() {
    while (true) {
      stations = stationService.getAll();
      System.out.println("--- Станції ---");
      System.out.println("1 - Переглянути всі станції");
      System.out.println("2 - Додати станцію");
      System.out.println("3 - Додати працівника для станції");
      System.out.println("4 - Видалити станцію");
      System.out.println("5 - Пошук станції");
      System.out.println("0 - Назад");
      System.out.print("Виберіть опцію: ");
      String choice = scanner.nextLine();
      switch (choice) {
        case "1" -> showAllSomething(stations, EntityName.STATIONS);
        case "2" -> addNewStation();
        case "3" -> addEmployeeForStation();
        case "4" -> deleteStation();
        case "5" -> searchStation();
        case "0" -> {
          return;
        }
        default -> System.out.println("Невірний вибір!");
      }
    }
  }

  private static void bicycleSubMenu() {
    while (true) {
      bicycles = bicycleService.getAll();

      System.out.println("Меню керування велосипедами:");
      System.out.println("1 - Переглянути всі велосипеди");
      System.out.println("2 - Додати новий велосипед");
      System.out.println("3 - Редагувати велосипед");
      System.out.println("4 - Видалити велосипед");
      System.out.println("0 - Повернутись в адмін меню");
      System.out.print("Виберіть дію: ");

      String choice = scanner.nextLine();

      switch (choice) {
        case "1" -> showAllSomething(bicycles, EntityName.BICYCLES);
        case "2" -> addNewBicycle();
        case "3" -> editBicycle();
        case "4" -> deleteBicycle();
        case "0" -> {
          return;
        }
        default -> System.out.println("Невірний вибір!");
      }

      System.out.println();
    }
  }

  private static void addNewUser() {
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

      if (userService.getByLogin(newUser.getLogin()) != null) {
        System.out.println("Упс! Схоже що даний логін занятий\nСпробуйте ще раз\n");
      } else {
        System.out.println(
            "Користувача додано! Логін: " + login + ", Роль: " + selectedRole.getName());
        userService.addNewUser(newUser);
      }
    } catch (CustomEntityValidationExeption exeption) {
      System.out.println(exeption.getMessage());
    }

  }

  private static void editUser() {
    showAllSomething(users, EntityName.USERS);
    System.out.println();
    System.out.println("Виберіть одного з користувачів для редагування");
    int index = choiseUser() - 1;
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
      System.out.println("Користувача успішно оновлено: " + currentUser.getLogin());
      currentUser.setLogin(tempUser.getLogin());
      currentUser.setEmail(tempUser.getEmail());
      currentUser.setPassword(tempUser.getPassword());

      userService.updateUser(currentUser);
    } catch (CustomEntityValidationExeption e) {
      System.out.println(e.getMessage());
    }
  }

  private static void deleteUser() {
    showAllSomething(users, EntityName.USERS);
    System.out.println();
    System.out.println("Виберіть одного з користувачів для видалення");
    int index = choiseUser() - 1;
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

  private static void searchUser() {
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

  private static void addNewStation() {
    System.out.print("Введіть назву станції: ");
    String name = scanner.nextLine();

    System.out.print("Введіть адресу (вул. <Назва>, <номер>, <Місто>): ");
    String address = scanner.nextLine();

    System.out.print("Введіть кількість місць: ");
    String countPlaces = scanner.nextLine();

    Station newStation;
    try {
      newStation = new Station(name, address, countPlaces);
      stationService.addNewStation(newStation);
      System.out.println("Успішно додано нову станцію");
    } catch (CustomEntityValidationExeption e) {
      System.out.println("Помилки при створенні станції:");
      System.out.println(e.getMessage());
    }
  }

  private static void addEmployeeForStation() {
    showAllSomething(stations, EntityName.STATIONS);
    System.out.println();
    System.out.println("Виберіть станцію для якої хочете додати працівника: ");
    int choiseStation = choiseUser() - 1;

    if (choiseStation < 0 || choiseStation >= stations.size()) {
      System.out.println("Невірний вибір станції!");
      return;
    }

    Station currentStation = stations.get(choiseStation);
    System.out.println();
    System.out.println("Виберіть працівника якого хочете призначити за надзором\n");
    showAllSomething(employees, EntityName.EMPLOYEES);

    int choiseEmployee = choiseUser() - 1;

    if (choiseEmployee < 0 || choiseEmployee >= employees.size()) {
      System.out.println("Невірний вибір працівника!");
      return;
    }

    Employee currentEmployee = employees.get(choiseEmployee);

    currentEmployee.setStationId(currentStation.getId());
    currentStation.setEmployeeId(currentEmployee.getId());

    employeeService.updateEmployee(currentEmployee);
    stationService.updateStation(currentStation);
    System.out.println("Успішне додавання працівника!");
  }

  private static void deleteStation() {
    showAllSomething(stations, EntityName.STATIONS);
    System.out.println();
    System.out.println("Виберіть станцію для видалення: ");
    int choiseStation = choiseUser() - 1;

    if (choiseStation < 0 || choiseStation >= stations.size()) {
      System.out.println("Невірний вибір станції!");
      return;
    }

    Station currentStation = stations.get(choiseStation);

    if (stationService.delete(currentStation)) {
      System.out.println("Успішне видалення");
    } else {
      System.out.println("Помилка при видаленні");
    }
  }

  private static void searchStation() {
    System.out.println("Введіть назву станції, яку хочете знайти: ");
    String searchStation = scanner.nextLine();
    Station station = stationService.getByName(searchStation);
    if (station != null) {
      System.out.println("Успішний пошук");
      System.out.println(station);
    } else {
      System.out.println("Невдалося знайти станцію");
    }
  }

  private static void addNewBicycle() {
    System.out.print("Введіть модель велосипеда: ");
    String model = scanner.nextLine();

    System.out.println("Виберіть тип велосипеда:");
    for (int i = 0; i < TypeBicycle.values().length; i++) {
      System.out.println((i + 1) + " - " + TypeBicycle.values()[i].getName());
    }
    int typeIndex = choiseUser() - 1;

    if (typeIndex < 0 || typeIndex >= TypeBicycle.values().length) {
      System.out.println("Невірний вибір");
      return;
    }
    TypeBicycle type = TypeBicycle.values()[typeIndex];

    System.out.print("Введіть ціну за годину: ");
    String price = scanner.nextLine();

    Bicycle bicycle;
    try {
      bicycle = new Bicycle(model, type, price);
      bicycleService.addNewBicycle(bicycle);
      System.out.println("Велосипед успішно додано!");
    } catch (CustomEntityValidationExeption e) {
      System.out.println(e.getMessage());
    }
  }

  private static void editBicycle() {
    showAllSomething(bicycles, EntityName.BICYCLES);
    System.out.println();
    System.out.println("Виберіть велосипед для редагування:");
    int index = choiseUser() - 1;
    if (index < 0 || index >= bicycles.size()) {
      System.out.println("Невірний вибір!");
      return;
    }

    Bicycle currentBicycle = bicycles.get(index);
    System.out.println("Редагування велосипеда: " + currentBicycle.getModel());

    String newModel;
    while (true) {
      System.out.print("Нова модель (обов'язково, поточна: " + currentBicycle.getModel() + "): ");
      newModel = scanner.nextLine();
      if (!newModel.isBlank()) {
        break;
      } else {
        System.out.println("Модель обов'язкова!");
      }
    }

    System.out.println(
        "Виберіть новий тип (Enter щоб пропустити, поточний: " + currentBicycle.getTypeBicycle()
            .getName() + "):");
    for (int i = 0; i < TypeBicycle.values().length; i++) {
      System.out.println((i + 1) + " - " + TypeBicycle.values()[i].getName());
    }
    String typeInput = scanner.nextLine();
    TypeBicycle newType = currentBicycle.getTypeBicycle();
    if (!typeInput.isBlank()) {
      try {
        int typeIndex = Integer.parseInt(typeInput) - 1;
        if (typeIndex >= 0 && typeIndex < TypeBicycle.values().length) {
          newType = TypeBicycle.values()[typeIndex];
        } else {
          System.out.println("Невірний вибір типу, залишено попередній.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Некоректне число, залишено попередній тип.");
      }
    }

    System.out.print(
        "Нова ціна за годину (Enter щоб пропустити, поточна: " + currentBicycle.getPricePerHour()
            + "): ");
    String newPriceStr = scanner.nextLine();
    double newPrice = currentBicycle.getPricePerHour();
    if (!newPriceStr.isBlank()) {
      try {
        newPrice = Double.parseDouble(newPriceStr);
      } catch (NumberFormatException e) {
        System.out.println("Некоректна ціна, залишено попередню.");
      }
    }

    try {
      Bicycle tempBicycle = new Bicycle(newModel, newType, String.valueOf(newPrice));
      currentBicycle.setModel(tempBicycle.getModel());
      currentBicycle.setTypeBicycle(tempBicycle.getTypeBicycle());
      currentBicycle.setPricePerHour(String.valueOf(tempBicycle.getPricePerHour()));

      bicycleService.updateBicycle(currentBicycle);
      System.out.println("Велосипед успішно оновлено: " + currentBicycle.getModel());
    } catch (CustomEntityValidationExeption e) {
      System.out.println("Помилки при оновленні велосипеда:");
      System.out.println(e.getMessage());
    }
  }


  private static void deleteBicycle() {
    showAllSomething(bicycles, EntityName.BICYCLES);
    System.out.println("Виберіть велосипед для видалення:");
    int index = choiseUser() - 1;
    if (index < 0 || index >= bicycles.size()) {
      System.out.println("Невірний вибір!");
      return;
    }

    Bicycle bicycle = bicycles.get(index);
    bicycleService.delete(bicycle);
    System.out.println("Велосипед видалено!");
  }

  private static int choiseUser() {
    while (true) {
      String input = scanner.nextLine();
      try {
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Упс, ви ввели не число! Спробуйте ще раз:");
      }
    }
  }

  private static void showAllSomething(List<? extends BaseEntity> entities, EntityName entityName) {
    if (entities == null || entities.isEmpty()) {
      System.out.println("Список " + entityName.getName() + " пустий!");
      return;
    }
    int count = 1;
    for (var entity : entities) {
      System.out.println(count + ". " + entity);
      count++;
    }
  }


}
