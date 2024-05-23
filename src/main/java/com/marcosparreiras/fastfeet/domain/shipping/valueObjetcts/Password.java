package com.marcosparreiras.fastfeet.domain.shipping.valueObjetcts;

import com.marcosparreiras.fastfeet.domain.common.boundaries.PasswordEncoder;

public class Password {

  private PasswordEncoder encoder;
  private String value;

  public Password(PasswordEncoder encoder, String plain) {
    this.encoder = encoder;
    this.value = encoder.hash(plain);
  }

  public String getValue() {
    return this.value;
  }

  public boolean compare(String plain) {
    return encoder.compare(plain, this.value);
  }
}
