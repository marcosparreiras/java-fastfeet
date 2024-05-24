package com.marcosparreiras.fastfeet.domain.common.exceptions;

public class UnauthorizedException extends DomainException {

  public UnauthorizedException() {
    super("Unauthorized", 403);
  }
}
