package com.yakim.bicyclesharing.presentation.pages.admin;

import com.yakim.bicyclesharing.presentation.enums.EntityName;
import com.yakim.bicyclesharing.presentation.forms.admin.station.AddEmployeeToStationForm;
import com.yakim.bicyclesharing.presentation.forms.admin.station.AddStationForm;
import com.yakim.bicyclesharing.presentation.forms.admin.station.DeleteStationForm;
import com.yakim.bicyclesharing.presentation.forms.admin.station.SearchStationForm;
import com.yakim.bicyclesharing.services.StationService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import java.util.Scanner;

public class StationView {

  private final StationService stationService = AppConfig.stationService();
  private final Scanner scanner = new Scanner(System.in);

  public void show() {
    while (true) {
      System.out.println("СТАНЦІЇ");
      System.out.println("1 - Переглянути всі станції");
      System.out.println("2 - Додати станцію");
      System.out.println("3 - Додати працівника до станції");
      System.out.println("4 - Видалити станцію");
      System.out.println("5 - Пошук станції");
      System.out.println("0 - Назад");

      String choice = scanner.nextLine();

      switch (choice) {
        case "1" -> ConsoleHelper.showAll(stationService.getAll(), EntityName.STATIONS);
        case "2" -> new AddStationForm().addNewStation();
        case "3" -> new AddEmployeeToStationForm().addEmployeeForStation();
        case "4" -> new DeleteStationForm().deleteStation();
        case "5" -> new SearchStationForm().searchStation();
        case "0" -> {
          return;
        }
        default -> System.out.println("Невірний вибір!");
      }
    }
  }
}
