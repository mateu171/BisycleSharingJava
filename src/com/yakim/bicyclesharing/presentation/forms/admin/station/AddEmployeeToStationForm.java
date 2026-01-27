package com.yakim.bicyclesharing.presentation.forms.admin.station;

import com.yakim.bicyclesharing.domain.Impl.Employee;
import com.yakim.bicyclesharing.domain.Impl.Station;
import com.yakim.bicyclesharing.presentation.enums.EntityName;
import com.yakim.bicyclesharing.services.EmployeeService;
import com.yakim.bicyclesharing.services.StationService;
import com.yakim.bicyclesharing.util.AppConfig;
import com.yakim.bicyclesharing.util.ConsoleHelper;
import java.util.List;

public class AddEmployeeToStationForm {

  private final EmployeeService employeeService = AppConfig.employeeService();
  private final StationService stationService = AppConfig.stationService();

  private List<Station> stations;
  private List<Employee> employees;

  public void addEmployeeForStation() {
    stations = stationService.getAll();
    employees = employeeService.getAll();
    ConsoleHelper.showAll(stations, EntityName.STATIONS);
    System.out.println();
    System.out.println("Виберіть станцію для якої хочете додати працівника: ");
    int choiseStation = ConsoleHelper.chooseUser() - 1;

    if (choiseStation < 0 || choiseStation >= stations.size()) {
      System.out.println("Невірний вибір станції!");
      return;
    }

    Station currentStation = stations.get(choiseStation);
    System.out.println();
    System.out.println("Виберіть працівника якого хочете призначити за надзором\n");
    ConsoleHelper.showAll(employees, EntityName.EMPLOYEES);

    int choiseEmployee = ConsoleHelper.chooseUser() - 1;

    if (choiseEmployee < 0 || choiseEmployee >= employees.size()) {
      System.out.println("Невірний вибір працівника!");
      return;
    }

    Employee currentEmployee = employees.get(choiseEmployee);

    currentEmployee.setStationId(currentStation.getId());
    currentStation.setEmployeeId(currentEmployee.getId());

    employeeService.update(currentEmployee);
    stationService.update(currentStation);
    System.out.println("Успішне додавання працівника!");
  }
}
