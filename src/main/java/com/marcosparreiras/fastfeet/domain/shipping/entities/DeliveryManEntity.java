package com.marcosparreiras.fastfeet.domain.shipping.entities;

import com.marcosparreiras.fastfeet.domain.common.UniqueEntityId;
import com.marcosparreiras.fastfeet.domain.shipping.valueObjetcts.Password;
import java.time.LocalDate;

public class DeliveryManEntity extends APersonEntity {

  private Password password;
  private boolean isAdmin;

  public Password getPassword() {
    return this.password;
  }

  public void setPassword(Password password) {
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
    Password password,
    boolean isAdmin
  ) {
    super(id, cpf, name, createdAt, updatedAt);
    this.password = password;
    this.isAdmin = isAdmin;
  }

  public static DeliveryManEntity create(
    String cpf,
    String name,
    Password password
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
    Password password,
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
