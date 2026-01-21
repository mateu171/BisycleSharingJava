package com.yakim.bicyclesharing.repository.json;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.domain.enums.StateBicycle;
import com.yakim.bicyclesharing.domain.enums.TypeBicycle;
import com.yakim.bicyclesharing.repository.BicycleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JsonBicycleRepository
    extends JsonRepository<Bicycle, UUID> implements
    BicycleRepository {

  public JsonBicycleRepository(String filename) {
    super(
        Bicycle::getId,
        filename,
        new TypeToken<ArrayList<Bicycle>>() {
        }.getType(),
        new GsonBuilder().setPrettyPrinting().serializeNulls().create()
    );
  }

  @Override
  public List<Bicycle> findByState(StateBicycle stateBicycle) {
    return findBy(s -> s.getState() == stateBicycle);
  }

  @Override
  public List<Bicycle> findByType(TypeBicycle type) {
    return findBy(t -> t.getTypeBicycle() == type);
  }
}
