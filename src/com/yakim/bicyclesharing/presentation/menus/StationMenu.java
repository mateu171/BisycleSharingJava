package com.yakim.bicyclesharing.presentation.menus;

import com.yakim.bicyclesharing.domain.Impl.Employee;
import com.yakim.bicyclesharing.domain.Impl.Station;
import com.yakim.bicyclesharing.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.services.EmployeeService;
import com.yakim.bicyclesharing.services.StationService;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import com.yakim.bicyclesharing.util.EntityName;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StationMenu {

  private final Scanner scanner = new Scanner(System.in);
  private final StationService stationService;
  private final EmployeeService employeeService;
  private List<Employee> employees = new ArrayList<>();
  private List<Station> stations = new ArrayList<>();

  public StationMenu(StationService stationService, EmployeeService employeeService) {
    this.stationService = stationService;
    this.employeeService = employeeService;
  }

  public void stationSubMenu() {
    while (true) {
      stations = stationService.getAll();
      employees = employeeService.getAll();
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
        case "1" -> ConsoleHelper.showAll(stations, EntityName.STATIONS);
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

  private void addNewStation() {
    System.out.print("Введіть назву станції: ");
    String name = scanner.nextLine();

    System.out.print("Введіть адресу (вул. <Назва>, <номер>, <Місто>): ");
    String address = scanner.nextLine();

    System.out.print("Введіть кількість місць: ");
    String countPlaces = scanner.nextLine();

    Station newStation;
    try {
      newStation = new Station(name, address, countPlaces);
      stationService.add(newStation);
      System.out.println("Успішно додано нову станцію");
    } catch (CustomEntityValidationExeption e) {
      System.out.println("Помилки при створенні станції:");
      System.out.println(e.getMessage());
    }
  }

  private void addEmployeeForStation() {
    ConsoleHelper.showAll(stations, EntityName.STATIONS);
    System.out.println();
    System.out.println("Виберіть станцію для якої хочете додати працівника: ");
    int choiseStation = ConsoleHelper.chooseUser() - 1;

    if (choiseStation < 0 || choiseStation >= stations.size()) {
      System.out.println("Невірний вибір станції!");
      return;
    }

    Station currentStation = stations.get(choiseStation);
    System.out.println();
    System.out.println("Виберіть працівника якого хочете призначити за надзором\n");
    ConsoleHelper.showAll(employees, EntityName.EMPLOYEES);

    int choiseEmployee = ConsoleHelper.chooseUser() - 1;

    if (choiseEmployee < 0 || choiseEmployee >= employees.size()) {
      System.out.println("Невірний вибір працівника!");
      return;
    }

    Employee currentEmployee = employees.get(choiseEmployee);

    currentEmployee.setStationId(currentStation.getId());
    currentStation.setEmployeeId(currentEmployee.getId());

    employeeService.update(currentEmployee);
    stationService.update(currentStation);
    System.out.println("Успішне додавання працівника!");
  }

  private void deleteStation() {
    ConsoleHelper.showAll(stations, EntityName.STATIONS);
    System.out.println();
    System.out.println("Виберіть станцію для видалення: ");
    int choiseStation = ConsoleHelper.chooseUser() - 1;

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

  private void searchStation() {
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

}
