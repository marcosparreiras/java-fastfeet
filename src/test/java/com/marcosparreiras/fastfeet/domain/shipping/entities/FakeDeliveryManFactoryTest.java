package com.marcosparreiras.fastfeet.domain.shipping.entities;

import com.github.javafaker.Faker;
import com.marcosparreiras.fastfeet.domain.common.boundaries.FakePasswordEncoder;
import com.marcosparreiras.fastfeet.domain.common.boundaries.PasswordEncoder;
import com.marcosparreiras.fastfeet.domain.shipping.valueObjetcts.Password;
import java.util.Random;

public class FakeDeliveryManFactoryTest {

  public static DeliveryManEntity createAdmin() {
    DeliveryManEntity admin = FakeDeliveryManFactoryTest.create();
    admin.setIsAdmin(true);
    return admin;
  }

  public static DeliveryManEntity createWithPassowrd(String password) {
    DeliveryManEntity deliveryMan = FakeDeliveryManFactoryTest.create();
    PasswordEncoder passwordEncoder = new FakePasswordEncoder();
    Password newPassword = new Password(passwordEncoder, password);
    deliveryMan.setPassword(newPassword);
    return deliveryMan;
  }

  public static DeliveryManEntity create() {
    Faker faker = new Faker();
    Random random = new Random();
    String cpf = "";
    for (int i = 1; i <= 11; i++) {
      cpf += String.valueOf(random.nextInt(10));
    }
    PasswordEncoder passwordEncoder = new FakePasswordEncoder();
    Password password = new Password(
      passwordEncoder,
      faker.internet().password()
    );
    return DeliveryManEntity.create(cpf, faker.name().fullName(), password);
  }
}
