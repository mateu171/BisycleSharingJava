package com.yakim.bicyclesharing.presentation.pages.user;

import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.domain.enums.StateBicycle;
import com.yakim.bicyclesharing.services.BicycleService;
import com.yakim.bicyclesharing.util.AppConfig;
import java.util.List;

public class AvailableBicyclesView {

  private final BicycleService bicycleService = AppConfig.bicycleService();
  private List<Bicycle> bicyclesAvailable;

  public void showAllAvailableBicycles() {

    bicyclesAvailable = bicycleService.getByState(StateBicycle.AVAILABLE);

    if (bicyclesAvailable.isEmpty()) {
      System.out.println("Нема доступних велосипедів для оренди");
      return;
    }

    int count = 1;
    System.out.println("Список всіх доступних велосипедів для оренди");
    for (var bicycle : bicyclesAvailable) {
      System.out.println(count + ". " + bicycle);
      count++;
    }
  }

}
