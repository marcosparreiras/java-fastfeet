package com.marcosparreiras.fastfeet.domain.shipping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marcosparreiras.fastfeet.domain.common.UniqueEntityId;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeliveryManEntityTest {

  @Test
  void shouldBeAbleToCreateANewDeliveryMan() {
    String cpf = "00000000000";
    String name = "John Doe";
    String password = "123456";
    DeliveryManEntity deliveryMan = DeliveryManEntity.create(
      cpf,
      name,
      password
    );
    assertThat(deliveryMan.getName()).isEqualTo(name);
    assertThat(deliveryMan.getCpf()).isEqualTo(cpf);
    assertThat(deliveryMan.getPassword()).isEqualTo(password);
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
    String password = "123456";
    boolean isAdmin = true;
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
    assertThat(deliveryMan.getPassword()).isEqualTo(password);
    assertThat(deliveryMan.getIsAdmin()).isEqualTo(isAdmin);

    deliveryMan.setPassword("654321");
    assertThat(deliveryMan.getUpdatedAt()).isNotNull();
  }
}
