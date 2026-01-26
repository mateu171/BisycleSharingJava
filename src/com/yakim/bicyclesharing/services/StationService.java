package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.Station;
import com.yakim.bicyclesharing.repository.Repository;
import com.yakim.bicyclesharing.repository.StationRepository;
import java.util.UUID;

public class StationService extends BaseService<Station, UUID> {

  private final StationRepository stationRepository;

  public StationService(StationRepository stationRepository) {
    this.stationRepository = stationRepository;
  }

  @Override
  protected Repository<Station, UUID> getRepository() {
    return stationRepository;
  }

  public Station getByName(String name) {
    return stationRepository.findByName(name);
  }
}
