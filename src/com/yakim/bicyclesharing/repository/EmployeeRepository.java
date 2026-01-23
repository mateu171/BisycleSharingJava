package com.yakim.bicyclesharing.repository;

import com.yakim.bicyclesharing.domain.Impl.Employee;
import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends Repository<Employee, UUID> {

  List<Employee> findByStationId(UUID id);

  List<Employee> findByName(String name);
}
