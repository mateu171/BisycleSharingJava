package com.yakim.bicyclesharing.util;

import com.yakim.bicyclesharing.domain.security.PasswordHasher;
import com.yakim.bicyclesharing.repository.json.JsonBicycleRepository;
import com.yakim.bicyclesharing.repository.json.JsonEmployeeRepository;
import com.yakim.bicyclesharing.repository.json.JsonRentalRepository;
import com.yakim.bicyclesharing.repository.json.JsonStationRepository;
import com.yakim.bicyclesharing.repository.json.JsonUserRepository;
import com.yakim.bicyclesharing.services.AuthService;
import com.yakim.bicyclesharing.services.BicycleService;
import com.yakim.bicyclesharing.services.EmailService;
import com.yakim.bicyclesharing.services.EmployeeService;
import com.yakim.bicyclesharing.services.RentalService;
import com.yakim.bicyclesharing.services.StationService;
import com.yakim.bicyclesharing.services.UserService;
import com.yakim.bicyclesharing.services.VerificationService;

public class AppConfig {

  public static VerificationService verificationService() {
    return new VerificationService(
        new EmailService(
        )
    );
  }

  public static UserService userService() {
    return new UserService(
        new JsonUserRepository("data/users.json"),
        new PasswordHasher()
    );
  }

  public static BicycleService bicycleService() {
    return new BicycleService(
        new JsonBicycleRepository("data/bicycles.json")
    );
  }

  public static EmployeeService employeeService() {
    return new EmployeeService(
        new JsonEmployeeRepository("data/employees.json")
    );
  }

  public static RentalService rentalService() {
    return new RentalService(
        new JsonRentalRepository("data/rentals.json"),
        bicycleService()
    );
  }

  public static StationService stationService() {
    return new StationService(new JsonStationRepository("data/stations.json"));
  }

  public static AuthService authService() {
    return new AuthService(new JsonUserRepository("data/users.json"));
  }
}
