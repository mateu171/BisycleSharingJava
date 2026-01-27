package com.yakim.bicyclesharing.presentation.forms.user;

import com.yakim.bicyclesharing.domain.Impl.Rental;
import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.presentation.pages.user.MyRentalsView;
import com.yakim.bicyclesharing.services.RentalService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import java.util.List;

public class ReturnBicycleForm {

  private final RentalService rentalService = AppConfig.rentalService();
  private final User user;
  private List<Rental> activeRentals;

  public ReturnBicycleForm(User user) {
    this.user = user;
  }

  public void giveBackBicycle() {
    activeRentals = rentalService.getByActiveAndUserId(user.getId());

    if (activeRentals.isEmpty()) {
      System.out.println("У вас нема активних оренд");
      return;
    }
    new MyRentalsView(user).showActiveRentals();
    System.out.println("Виберіть одну з оренд, яку хочете скасувати");
    int choice = ConsoleHelper.chooseUser() - 1;
    if (choice < 0 || choice >= activeRentals.size()) {
      System.out.println("В списку за даним номером нема такої оренди");
    } else {
      Rental currentRental = activeRentals.get(choice);

      currentRental = rentalService.finishRental(currentRental);

      System.out.println(
          "Ви повернули велосипед. Загальна вартість: " + currentRental.getTotalCost() + " грн");
    }
  }
}
