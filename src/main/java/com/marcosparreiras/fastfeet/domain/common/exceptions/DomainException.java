package com.marcosparreiras.fastfeet.domain.common.exceptions;

public class DomainException extends Exception {

  private int statusCode;

  public int getStatusCode() {
    return this.statusCode;
  }

  public DomainException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }
}
