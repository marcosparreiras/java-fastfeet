package com.marcosparreiras.fastfeet.domain.common.exceptions;

public class DeliveryManNotFoundException extends DomainException {

  public DeliveryManNotFoundException() {
    super("Delivery man not found", 404);
  }
}
