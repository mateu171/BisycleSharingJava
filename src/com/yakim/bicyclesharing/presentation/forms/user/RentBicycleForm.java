package com.yakim.bicyclesharing.presentation.forms.user;

import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.domain.Impl.Rental;
import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.domain.enums.StateBicycle;
import com.yakim.bicyclesharing.presentation.pages.user.AvailableBicyclesView;
import com.yakim.bicyclesharing.services.BicycleService;
import com.yakim.bicyclesharing.services.RentalService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import java.util.List;
import java.util.UUID;

public class RentBicycleForm {

  private final BicycleService bicycleService = AppConfig.bicycleService();
  private final RentalService rentalService = AppConfig.rentalService();
  private final User currentUser;
  private List<Bicycle> bicyclesAvailable;

  public RentBicycleForm(User currentUser) {
    this.currentUser = currentUser;
  }

  public void arrangeRental() {
    bicyclesAvailable = bicycleService.getByState(StateBicycle.AVAILABLE);

    if (bicyclesAvailable.isEmpty()) {
      System.out.println("Нема доступних велосипедів для оренди");
      return;
    }
    new AvailableBicyclesView().showAllAvailableBicycles();
    System.out.println();
    System.out.println("Виберіть велосипед для оренди");
    int choice = ConsoleHelper.chooseUser() - 1;
    if (choice < 0 || choice >= bicyclesAvailable.size()) {
      System.out.println("В списку за даним номером нема такого велосипеда");
    } else {
      Bicycle rented = bicyclesAvailable.get(choice);
      System.out.println("Успішна оренда!");
      Rental currentRental = rentalService.add(
          new Rental(currentUser.getId(), rented.getId(), UUID.randomUUID()));
      rented.setState(StateBicycle.RENTED);
      rented.setRentalId(currentRental.getId());
      bicycleService.update(rented);
    }
  }
}
