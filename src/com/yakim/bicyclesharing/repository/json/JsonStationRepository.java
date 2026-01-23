package com.yakim.bicyclesharing.repository.json;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yakim.bicyclesharing.domain.Impl.Station;
import com.yakim.bicyclesharing.repository.StationRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonStationRepository extends JsonRepository<Station, UUID>
    implements StationRepository {

  public JsonStationRepository(String filename) {
    super(
        Station::getId,
        filename,
        new TypeToken<ArrayList<Station>>() {
        }.getType(),
        new GsonBuilder().setPrettyPrinting().serializeNulls().create()
    );
  }

  @Override
  public Station findByName(String name) {
    List<Station> result = findBy(s -> s.getName() != null &&
        s.getName().equalsIgnoreCase(name));
    return result.isEmpty() ? null : result.get(0);
  }
}
