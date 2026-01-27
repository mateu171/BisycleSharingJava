package com.yakim.bicyclesharing.presentation.forms.admin.station;

import com.yakim.bicyclesharing.domain.Impl.Station;
import com.yakim.bicyclesharing.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.services.StationService;
import com.yakim.bicyclesharing.util.AppConfig;
import java.util.Scanner;

public class AddStationForm {


  private final Scanner scanner = new Scanner(System.in);
  private final StationService stationService = AppConfig.stationService();

  public void addNewStation() {
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
}
