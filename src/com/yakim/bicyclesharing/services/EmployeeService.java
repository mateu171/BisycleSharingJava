package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.Employee;
import com.yakim.bicyclesharing.repository.EmployeeRepository;
import com.yakim.bicyclesharing.repository.Repository;
import java.util.List;
import java.util.UUID;

public class EmployeeService extends BaseService<Employee, UUID> {

  private final EmployeeRepository employeeRepository;

  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  protected Repository<Employee, UUID> getRepository() {
    return employeeRepository;
  }

  public List<Employee> getByStationId(UUID stationId) {
    return employeeRepository.findByStationId(stationId);
  }

  public List<Employee> getByName(String name) {
    return employeeRepository.findByName(name);
  }

}
