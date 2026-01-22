package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.Rental;
import com.yakim.bicyclesharing.domain.enums.RentalStatus;
import com.yakim.bicyclesharing.repository.RentalRepository;
import com.yakim.bicyclesharing.repository.json.JsonRentalRepository;
import java.util.List;
import java.util.UUID;

public class RentalService {

  private final RentalRepository repository = new JsonRentalRepository("data/rentals.json");

  public List<Rental> getAll() {
    return repository.findAll();
  }

  public Rental addNewRental(Rental newRental) {
    return repository.save(newRental);
  }

  public boolean delete(UUID id) {
    return repository.deleteById(id);
  }

  public Rental updateRental(Rental updatedRental) {
    return repository.update(updatedRental);
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
