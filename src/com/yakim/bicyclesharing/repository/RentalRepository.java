package com.yakim.bicyclesharing.repository;

import com.yakim.bicyclesharing.domain.Impl.Rental;
import com.yakim.bicyclesharing.domain.enums.RentalStatus;
import java.util.List;
import java.util.UUID;

public interface RentalRepository extends Repository<Rental, UUID> {

  List<Rental> findByUserId(UUID id);

  List<Rental> findByStationId(UUID id);

  List<Rental> findByRentalStatus(RentalStatus rentalStatus);
}
