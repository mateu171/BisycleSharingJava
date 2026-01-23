package com.yakim.bicyclesharing.services;

import com.yakim.bicyclesharing.domain.Impl.Station;
import com.yakim.bicyclesharing.repository.StationRepository;
import com.yakim.bicyclesharing.repository.json.JsonStationRepository;
import java.util.List;
import java.util.UUID;

public class StationService {

  private final StationRepository stationRepository =
      new JsonStationRepository("data/stations.json");

  public Station addNewStation(Station station) {
    return stationRepository.save(station);
  }

  public List<Station> getAll() {
    return stationRepository.findAll();
  }

  public Station getByName(String name) {
    return stationRepository.findByName(name);
  }

  public boolean delete(UUID id) {
    return stationRepository.deleteById(id);
  }

  public boolean delete(Station station) {
    return stationRepository.delete(station);
  }

  public Station updateStation(Station updatedStation) {
    return stationRepository.update(updatedStation);
  }
}
