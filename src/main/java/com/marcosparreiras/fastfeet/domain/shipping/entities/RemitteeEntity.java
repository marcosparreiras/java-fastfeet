package com.marcosparreiras.fastfeet.domain.shipping.entities;

import com.marcosparreiras.fastfeet.domain.common.UniqueEntityId;
import java.time.LocalDate;

public class RemitteeEntity extends APersonEntity {

  private RemitteeEntity(
    UniqueEntityId id,
    String cpf,
    String name,
    LocalDate createdAt,
    LocalDate updatedAt
  ) {
    super(id, cpf, name, createdAt, updatedAt);
  }

  public static RemitteeEntity create(String cpf, String name) {
    UniqueEntityId id = new UniqueEntityId();
    LocalDate createdAt = LocalDate.now();
    LocalDate updatedAt = null;
    return new RemitteeEntity(id, cpf, name, createdAt, updatedAt);
  }

  public static RemitteeEntity load(
    UniqueEntityId id,
    String cpf,
    String name,
    LocalDate createdAt,
    LocalDate updatedAt
  ) {
    return new RemitteeEntity(id, cpf, name, createdAt, updatedAt);
  }
}
