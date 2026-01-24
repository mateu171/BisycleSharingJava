package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.Rental;
import com.yakim.bicyclesharing.domain.enums.RentalStatus;
import com.yakim.bicyclesharing.repository.RentalRepository;
import com.yakim.bicyclesharing.repository.Repository;
import com.yakim.bicyclesharing.repository.json.JsonRentalRepository;
import java.util.List;
import java.util.UUID;

public class RentalService extends BaseService<Rental, UUID> {

  private final RentalRepository repository = new JsonRentalRepository("data/rentals.json");

  @Override
  protected Repository<Rental, UUID> getRepository() {
    return repository;
  }

  public List<Rental> getByUserId(UUID id) {
    return repository.findByUserId(id);
  }

  public List<Rental> getByStationId(UUID id) {
    return repository.findByStationId(id);
  }

  public List<Rental> getByActiveRentals() {
    return repository.findByRentalStatus(RentalStatus.ACTIVE);
  }

  public List<Rental> getByActiveAndUserId(UUID id) {
    return repository.findByUserId(id).stream()
        .filter(s -> s.getRentalStatus().equals(RentalStatus.ACTIVE)).toList();
  }
}
