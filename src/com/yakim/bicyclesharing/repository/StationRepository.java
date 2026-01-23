package com.yakim.bicyclesharing.repository;

import com.yakim.bicyclesharing.domain.Impl.Station;
import java.util.UUID;

public interface StationRepository extends Repository<Station, UUID> {

  Station findByName(String name);
}
