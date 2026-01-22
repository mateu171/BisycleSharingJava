package com.yakim.bicyclesharing.repository.json;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yakim.bicyclesharing.domain.Impl.Rental;
import com.yakim.bicyclesharing.domain.enums.RentalStatus;
import com.yakim.bicyclesharing.repository.RentalRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonRentalRepository
    extends JsonRepository<Rental, UUID>
    implements RentalRepository {

  public JsonRentalRepository(String filename) {
    super(Rental::getId,
        filename,
        new TypeToken<ArrayList<Rental>>() {
        }.getType(),
        new GsonBuilder().setPrettyPrinting().serializeNulls().create());
  }

  @Override
  public List<Rental> findByUserId(UUID id) {
    return findBy(u -> u.getUserId().equals(id));
  }

  @Override
  public List<Rental> findByStationId(UUID id) {
    return findBy(u -> u.getStationId().equals(id));
  }

  @Override
  public List<Rental> findByRentalStatus(RentalStatus rentalStatus) {
    return findBy(s -> s.getRentalStatus().equals(rentalStatus));
  }
}
