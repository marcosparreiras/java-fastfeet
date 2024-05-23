package com.marcosparreiras.fastfeet.domain.common.boundaries;

public interface PasswordEncoder {
  public String hash(String plain);

  public boolean compare(String plain, String hash);
}
