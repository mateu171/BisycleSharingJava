package com.yakim.bicyclesharing.domain.Impl;

import com.yakim.bicyclesharing.domain.enums.StateBicycle;
import com.yakim.bicyclesharing.domain.enums.TypeBicycle;
import com.yakim.bicyclesharing.domain.exeption.CustomEntityValidationExeption;
import com.yakim.bicyclesharing.domain.util.BaseEntity;
import java.util.UUID;

public class Bicycle extends BaseEntity {

  private String model;
  private TypeBicycle typeBicycle;
  private StateBicycle state;
  private double pricePerHour;
  private UUID rentalId;

  private Bicycle() {
    super();
  }

  public Bicycle(String model, TypeBicycle typeBicycle, String pricePerHour,
      UUID rentalId) {
    this();
    setModel(model);
    setTypeBicycle(typeBicycle);
    this.state = StateBicycle.MAINTENANCE;
    setPricePerHour(pricePerHour);
    setRentalId(rentalId);

    if (!isValid()) {
      throw new CustomEntityValidationExeption(getErrors());
    }
  }

  public UUID getRentalId() {
    return rentalId;
  }

  public void setRentalId(UUID rentalId) {
    cleanErrors("rentalId");
    if (rentalId == null) {
      addError("rentalId", "Оренда не може бути пустою");
    }
    this.rentalId = rentalId;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    cleanErrors("model");
    if (model == null || model.trim().isEmpty()) {
      addError("model", "Моделя для велосипеда не повинна бути пустою");
    } else if (model.length() < 4 || model.length() > 15) {
      addError("model", "Модель велосипеда повинна бути не менше 4 та не більше 15 символів");
    }
    this.model = model;
  }

  public TypeBicycle getTypeBicycle() {
    return typeBicycle;
  }

  public void setTypeBicycle(TypeBicycle typeBicycle) {
    cleanErrors("typeBicycle");
    if (typeBicycle == null) {
      addError("typeBicycle", "Тип велосипеду не повинен бути пустим");
    }
    this.typeBicycle = typeBicycle;
  }

  public StateBicycle getState() {
    return state;
  }

  public void setState(StateBicycle state) {
    this.state = state;
  }

  public double getPricePerHour() {
    return pricePerHour;
  }

  public void setPricePerHour(String priceStr) {
    cleanErrors("pricePerHour");

    if (priceStr == null || priceStr.trim().isEmpty()) {
      addError("pricePerHour", "Ціна за годину не може бути пустою!");
      return;
    }

    try {
      double price = Double.parseDouble(priceStr);
      if (price < 0) {
        addError("pricePerHour", "Ціна за годину не може бути менше 0!");
      } else {
        this.pricePerHour = price;
      }
    } catch (NumberFormatException e) {
      addError("pricePerHour", "Ціна за годину повинна бути числом!");
    }
  }
}
