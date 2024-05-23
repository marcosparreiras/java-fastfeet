package com.marcosparreiras.fastfeet.domain.shipping.valueObjects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marcosparreiras.fastfeet.domain.common.boundaries.FakePasswordEncoder;
import com.marcosparreiras.fastfeet.domain.shipping.valueObjetcts.Password;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PasswordTest {

  @Test
  void shouldBeAbleToGenerateAHashedPassword() {
    String plainPassword = "123456";
    FakePasswordEncoder fakePasswordEncoder = new FakePasswordEncoder();
    Password password = new Password(fakePasswordEncoder, plainPassword);
    assertThat(password.getValue()).isNotEqualTo(plainPassword);
  }

  @Test
  void shouldBeAbleToValidateACorrectPassword() {
    String plainPassword = "123456";
    FakePasswordEncoder fakePasswordEncoder = new FakePasswordEncoder();
    Password password = new Password(fakePasswordEncoder, plainPassword);
    assertTrue(password.compare(plainPassword));
  }

  @Test
  void shouldBeAbleToInvalidateAnInvalidPassword() {
    String plainPassword = "123456";
    FakePasswordEncoder fakePasswordEncoder = new FakePasswordEncoder();
    Password password = new Password(fakePasswordEncoder, plainPassword);
    assertFalse(password.compare("654321"));
  }
}
