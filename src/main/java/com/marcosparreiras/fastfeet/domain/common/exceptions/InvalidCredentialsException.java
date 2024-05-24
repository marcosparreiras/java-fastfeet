package com.marcosparreiras.fastfeet.domain.common.exceptions;

public class InvalidCredentialsException extends DomainException {

  public InvalidCredentialsException() {
    super("Invalid credentials", 401);
  }
}
