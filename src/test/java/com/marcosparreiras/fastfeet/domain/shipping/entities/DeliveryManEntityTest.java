package com.marcosparreiras.fastfeet.domain.shipping.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marcosparreiras.fastfeet.domain.common.UniqueEntityId;
import com.marcosparreiras.fastfeet.domain.common.boundaries.FakePasswordEncoder;
import com.marcosparreiras.fastfeet.domain.shipping.valueObjetcts.Password;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeliveryManEntityTest {

  @Test
  void shouldBeAbleToCreateANewDeliveryMan() {
    String cpf = "00000000000";
    String name = "John Doe";
    String plainPassword = "123456";

    FakePasswordEncoder fakePasswordEncoder = new FakePasswordEncoder();
    Password password = new Password(fakePasswordEncoder, plainPassword);

    DeliveryManEntity deliveryMan = DeliveryManEntity.create(
      cpf,
      name,
      password
    );

    assertThat(deliveryMan.getName()).isEqualTo(name);
    assertThat(deliveryMan.getCpf()).isEqualTo(cpf);
    assertTrue(deliveryMan.validatePassword(plainPassword));
    assertThat(deliveryMan.getIsAdmin()).isEqualTo(false);

    assertThat(deliveryMan.getId()).isNotNull();
    assertThat(deliveryMan.getUpdatedAt()).isNull();

    deliveryMan.setIsAdmin(true);
    assertThat(deliveryMan.getUpdatedAt()).isNotNull();
    assertThat(deliveryMan.getIsAdmin()).isEqualTo(true);
  }

  @Test
  void shouldBeAbleToLoadAnExistentRemittee() {
    UniqueEntityId id = new UniqueEntityId();
    String cpf = "00000000000";
    String name = "John Doe";
    LocalDate createdAt = LocalDate.now();
    LocalDate updatedAt = null;
    String plainPassword = "123456";
    boolean isAdmin = true;

    FakePasswordEncoder fakePasswordEncoder = new FakePasswordEncoder();
    Password password = new Password(fakePasswordEncoder, plainPassword);

    DeliveryManEntity deliveryMan = DeliveryManEntity.load(
      id,
      cpf,
      name,
      createdAt,
      updatedAt,
      password,
      isAdmin
    );

    assertTrue(deliveryMan.getId().equal(id));
    assertThat(deliveryMan.getName()).isEqualTo(name);

    assertThat(deliveryMan.getCpf()).isEqualTo(cpf);
    assertThat(deliveryMan.getCreatedAt()).isEqualTo(createdAt);
    assertThat(deliveryMan.getUpdatedAt()).isNull();
    assertThat(deliveryMan.validatePassword(plainPassword));
    assertThat(deliveryMan.getIsAdmin()).isEqualTo(isAdmin);

    deliveryMan.setPassword(new Password(fakePasswordEncoder, "654321"));
    assertThat(deliveryMan.getUpdatedAt()).isNotNull();
  }
}
