package com.marcosparreiras.fastfeet.domain.shipping.useCases.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.marcosparreiras.fastfeet.domain.common.UniqueEntityId;
import com.marcosparreiras.fastfeet.domain.common.boundaries.InMemoryDeliveryManRepositoryTest;
import com.marcosparreiras.fastfeet.domain.common.exceptions.UnauthorizedException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import com.marcosparreiras.fastfeet.domain.shipping.entities.FakeDeliveryManFactoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AssertDeliveryManIsAdminTest {

  private InMemoryDeliveryManRepositoryTest deliveryManRepository;
  DeliveryManEntity deliveryMan = FakeDeliveryManFactoryTest.create();
  DeliveryManEntity admin = FakeDeliveryManFactoryTest.createAdmin();

  @BeforeEach
  void BeforeEach() {
    deliveryManRepository = new InMemoryDeliveryManRepositoryTest();
    deliveryManRepository.items.add(deliveryMan);
    deliveryManRepository.items.add(admin);
  }

  @Test
  void shouldAssertThatADeliveryManIsAdminById() {
    try {
      boolean isAdmin = AssertDeliveryManIsAdmin.execute(
        admin.getId().getValue().toString(),
        deliveryManRepository
      );
      assertTrue(isAdmin);
    } catch (Exception e) {
      fail("Unexpected exception", e);
    }
  }

  @Test
  void shouldThrowAnExcepitonWhenDeliveryManIsNotAnAdmin() {
    try {
      AssertDeliveryManIsAdmin.execute(
        deliveryMan.getId().getValue().toString(),
        deliveryManRepository
      );
      fail("Should throw an exception when delivery man is not an admin");
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UnauthorizedException.class);
    }
  }

  @Test
  void shouldThrowAnExcepitonWhenDeliveryManNotExists() {
    try {
      UniqueEntityId id = new UniqueEntityId();
      AssertDeliveryManIsAdmin.execute(
        id.getValue().toString(),
        deliveryManRepository
      );
      fail("Should throw an exception when delivery man is not an admin");
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UnauthorizedException.class);
    }
  }
}
