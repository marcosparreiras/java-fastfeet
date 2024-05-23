package com.marcosparreiras.fastfeet.domain.common.boundaries;

public class FakePasswordEncoder implements PasswordEncoder {

  @Override
  public String hash(String plain) {
    return plain.concat("-hashed");
  }

  @Override
  public boolean compare(String plain, String hash) {
    return hash.equals(hash(plain));
  }
}
