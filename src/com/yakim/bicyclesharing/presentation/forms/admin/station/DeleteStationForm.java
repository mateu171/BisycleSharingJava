package com.yakim.bicyclesharing.presentation.forms.admin.station;

import com.yakim.bicyclesharing.domain.Impl.Station;
import com.yakim.bicyclesharing.presentation.enums.EntityName;
import com.yakim.bicyclesharing.services.StationService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import java.util.List;

public class DeleteStationForm {

  private final StationService stationService = AppConfig.stationService();
  private List<Station> stations;

  public void deleteStation() {
    stations = stationService.getAll();
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
}
