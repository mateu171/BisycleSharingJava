package com.yakim.bicyclesharing.presentation.forms.admin.bicycle;

import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.presentation.enums.EntityName;
import com.yakim.bicyclesharing.services.BicycleService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import java.util.List;

public class DeleteBicycleForm {

  private final BicycleService bicycleService = AppConfig.bicycleService();
  private List<Bicycle> bicycles;

  public void deleteBicycle() {
    bicycles = bicycleService.getAll();

    ConsoleHelper.showAll(bicycles, EntityName.BICYCLES);
    System.out.println("Виберіть велосипед для видалення:");
    int index = ConsoleHelper.chooseUser() - 1;
    if (index < 0 || index >= bicycles.size()) {
      System.out.println("Невірний вибір!");
      return;
    }

    Bicycle bicycle = bicycles.get(index);
    if (bicycleService.delete(bicycle)) {
      System.out.println("Велосипед видалено!");
    } else {
      System.out.println("Невдалося видалити");
    }
  }
}
