package com.yakim.bicyclesharing.repository.json;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yakim.bicyclesharing.domain.Impl.Employee;
import com.yakim.bicyclesharing.repository.EmployeeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class JsonEmployeeRepository extends JsonRepository<Employee, UUID> implements
    EmployeeRepository {

  public JsonEmployeeRepository(String filename) {
    super(
        Employee::getId,
        filename,
        new TypeToken<ArrayList<Employee>>() {
        }.getType(),
        new GsonBuilder().setPrettyPrinting().serializeNulls().create()
    );
  }

  @Override
  public List<Employee> findByStationId(UUID id) {
    return findBy(e -> id.equals(e.getStationId()));
  }

  @Override
  public List<Employee> findByName(String name) {
    return findBy(e -> Objects.equals(e.getName(), name));
  }
}
