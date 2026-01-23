package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.Employee;
import com.yakim.bicyclesharing.repository.EmployeeRepository;
import com.yakim.bicyclesharing.repository.json.JsonEmployeeRepository;
import java.util.List;
import java.util.UUID;

public class EmployeeService {

  private final EmployeeRepository employeeRepository =
      new JsonEmployeeRepository("data/employees.json");

  public Employee addNewEmployee(Employee newEmployee) {
    return employeeRepository.save(newEmployee);
  }

  public List<Employee> getAll() {
    return employeeRepository.findAll();
  }

  public List<Employee> getByStationId(UUID stationId) {
    return employeeRepository.findByStationId(stationId);
  }

  public List<Employee> getByName(String name) {
    return employeeRepository.findByName(name);
  }

  public boolean delete(UUID id) {
    return employeeRepository.deleteById(id);
  }

  public boolean delete(Employee employee) {
    return employeeRepository.delete(employee);
  }

  public Employee updateEmployee(Employee updatedEmployee) {
    return employeeRepository.update(updatedEmployee);
  }
}
