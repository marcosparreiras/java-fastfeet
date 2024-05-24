package com.marcosparreiras.fastfeet.domain.common.exceptions;

public class DeliveryManAlreadyExistsException extends DomainException {

  public DeliveryManAlreadyExistsException() {
    super("Delivery man already exists", 400);
  }
}
