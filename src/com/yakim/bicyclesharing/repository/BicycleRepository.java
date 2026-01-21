package com.yakim.bicyclesharing.repository;

import com.yakim.bicyclesharing.domain.Impl.Bicycle;
import com.yakim.bicyclesharing.domain.enums.StateBicycle;
import com.yakim.bicyclesharing.domain.enums.TypeBicycle;
import java.util.List;
import java.util.UUID;

public interface BicycleRepository extends Repository<Bicycle, UUID> {

  List<Bicycle> findByState(StateBicycle stateBicycle);

  List<Bicycle> findByType(TypeBicycle type);
}
