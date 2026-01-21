package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.domain.enums.StateBicycle;
import com.yakim.bicyclesharing.domain.enums.TypeBicycle;
import com.yakim.bicyclesharing.repository.BicycleRepository;
import java.util.List;
import java.util.UUID;

public class BicycleService {

  private final BicycleRepository repository;

  public BicycleService(BicycleRepository repository) {
    this.repository = repository;
  }

  public Bicycle addNewBicycle(Bicycle bicycle) {
    return repository.save(bicycle);
  }

  public List<Bicycle> getAll() {
    return repository.findAll();
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
}

