package com.marcosparreiras.fastfeet.domain.shipping.entities;

import com.marcosparreiras.fastfeet.domain.common.UniqueEntityId;
import java.time.LocalDate;

public abstract class APersonEntity {

  protected UniqueEntityId id;
  protected String cpf;
  protected String name;
  protected LocalDate createdAt;
  protected LocalDate updatedAt;

  protected APersonEntity(
    UniqueEntityId id,
    String cpf,
    String name,
    LocalDate createdAt,
    LocalDate updatedAt
  ) {
    this.id = id;
    this.cpf = cpf;
    this.name = name;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public UniqueEntityId getId() {
    return this.id;
  }

  public String getCpf() {
    return this.cpf;
  }

  public void setCpf(String cpf) {
    this.touch();
    this.cpf = cpf;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.touch();
    this.name = name;
  }

  public LocalDate getCreatedAt() {
    return this.createdAt;
  }

  public LocalDate getUpdatedAt() {
    return this.updatedAt;
  }

  protected void touch() {
    this.updatedAt = LocalDate.now();
  }
}
