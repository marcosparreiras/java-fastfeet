package com.marcosparreiras.fastfeet.domain.shipping.entities;

import com.marcosparreiras.fastfeet.domain.common.UniqueEntityId;
import java.time.LocalDate;

public class DeliveryManEntity extends APersonEntity {

  private String password;
  private boolean isAdmin;

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.touch();
    this.password = password;
  }

  public boolean getIsAdmin() {
    return this.isAdmin;
  }

  public void setIsAdmin(boolean isAdmin) {
    this.touch();
    this.isAdmin = isAdmin;
  }

  private DeliveryManEntity(
    UniqueEntityId id,
    String cpf,
    String name,
    LocalDate createdAt,
    LocalDate updatedAt,
    String password,
    boolean isAdmin
  ) {
    super(id, cpf, name, createdAt, updatedAt);
    this.password = password;
    this.isAdmin = isAdmin;
  }

  public static DeliveryManEntity create(
    String cpf,
    String name,
    String password
  ) {
    UniqueEntityId id = new UniqueEntityId();
    LocalDate createdAt = LocalDate.now();
    LocalDate updatedAt = null;
    boolean isAdmin = false;
    return new DeliveryManEntity(
      id,
      cpf,
      name,
      createdAt,
      updatedAt,
      password,
      isAdmin
    );
  }

  public static DeliveryManEntity load(
    UniqueEntityId id,
    String cpf,
    String name,
    LocalDate createdAt,
    LocalDate updatedAt,
    String password,
    boolean isAdmin
  ) {
    return new DeliveryManEntity(
      id,
      cpf,
      name,
      createdAt,
      updatedAt,
      password,
      isAdmin
    );
  }
}
