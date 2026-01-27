package com.yakim.bicyclesharing.presentation.forms.admin.station;

import com.yakim.bicyclesharing.domain.Impl.Station;
import com.yakim.bicyclesharing.services.StationService;
import com.yakim.bicyclesharing.util.AppConfig;
import java.util.Scanner;

public class SearchStationForm {

  private final Scanner scanner = new Scanner(System.in);
  private final StationService stationService = AppConfig.stationService();

  public void searchStation() {
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
