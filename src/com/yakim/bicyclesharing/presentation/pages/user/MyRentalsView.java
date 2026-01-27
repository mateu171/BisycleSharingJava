package com.yakim.bicyclesharing.presentation.pages.user;

import com.yakim.bicyclesharing.domain.Impl.Rental;
import com.yakim.bicyclesharing.domain.Impl.User;
import com.yakim.bicyclesharing.services.RentalService;
import com.yakim.bicyclesharing.util.AppConfig;
import java.util.List;

public class MyRentalsView {

  private final RentalService rentalService = AppConfig.rentalService();
  private final User currentUser;

  private List<Rental> activeRentals;

  public MyRentalsView(User currentUser) {
    this.currentUser = currentUser;
  }

  public void showActiveRentals() {
    activeRentals = rentalService.getByActiveAndUserId(currentUser.getId());

    if (activeRentals.isEmpty()) {
      System.out.println("Увас нема активних оренд");
      return;
    }
    int count = 1;
    for (var rental : activeRentals) {
      System.out.println(count + ". " + rental);
      count++;
    }
  }
}
