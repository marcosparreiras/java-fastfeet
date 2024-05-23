package com.marcosparreiras.fastfeet.domain.common;

import java.util.UUID;

public class UniqueEntityId {

  private UUID value;

  public UUID getValue() {
    return this.value;
  }

  public boolean equal(UniqueEntityId uniqueEntityId) {
    return this.value.equals(uniqueEntityId.getValue());
  }

  public UniqueEntityId() {
    this.value = UUID.randomUUID();
  }

  public UniqueEntityId(UUID value) {
    this.value = value;
  }
}
