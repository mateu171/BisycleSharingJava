package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.domain.enums.StateBicycle;
import com.yakim.bicyclesharing.domain.enums.TypeBicycle;
import com.yakim.bicyclesharing.repository.BicycleRepository;
import com.yakim.bicyclesharing.repository.Repository;
import com.yakim.bicyclesharing.repository.json.JsonBicycleRepository;
import java.util.List;
import java.util.UUID;

public class BicycleService extends BaseService<Bicycle, UUID> {

  private final BicycleRepository repository = new JsonBicycleRepository("data/bicycles.json");

  @Override
  protected Repository<Bicycle, UUID> getRepository() {
    return repository;
  }

  public Bicycle getById(UUID id) {
    return repository.findById(id).orElse(null);
  }

  public List<Bicycle> getByState(StateBicycle stateBicycle) {
    return repository.findByState(stateBicycle);
  }

  public List<Bicycle> getByType(TypeBicycle typeBicycle) {
    return repository.findByType(typeBicycle);
  }
}

