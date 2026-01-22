package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.domain.enums.StateBicycle;
import com.yakim.bicyclesharing.domain.enums.TypeBicycle;
import com.yakim.bicyclesharing.repository.BicycleRepository;
import com.yakim.bicyclesharing.repository.json.JsonBicycleRepository;
import java.util.List;
import java.util.UUID;

public class BicycleService {

  private final BicycleRepository repository = new JsonBicycleRepository("data/bicycles.json");

  public Bicycle addNewBicycle(Bicycle bicycle) {
    return repository.save(bicycle);
  }

  public List<Bicycle> getAll() {
    return repository.findAll();
  }

  public Bicycle getById(UUID id) {
    return repository.findById(id).get();
  }

  public List<Bicycle> getByState(StateBicycle stateBicycle) {
    return repository.findByState(stateBicycle);
  }

  public List<Bicycle> getByType(TypeBicycle typeBicycle) {
    return repository.findByType(typeBicycle);
  }

  public boolean delete(UUID id) {
    return repository.deleteById(id);
  }

  public Bicycle updateBicycle(Bicycle updatedBicycle) {
    return repository.update(updatedBicycle);
  }
}

