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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {

  private static final Properties PROPERTIES = new Properties();

  static {
    try (FileInputStream fis = new FileInputStream("config.properties")) {
      PROPERTIES.load(fis);
    } catch (IOException e) {
      throw new RuntimeException(
          "Не вдалося завантажити файл config.properties. " +
              "Будь ласка, налаштуйте параметри електронної пошти.", e
      );
    }
  }

  private static String getProperty(String key) {
    String value = PROPERTIES.getProperty(key);
    if (value == null || value.isBlank()) {
      throw new IllegalStateException(
          "Відсутній обовʼязковий параметр: " + key +
              " у файлі config.properties"
      );
    }
    return value;
  }

  public static VerificationService verificationService() {
    return new VerificationService(
        new EmailService(
            getProperty("email.from"),
            getProperty("email.password")
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
