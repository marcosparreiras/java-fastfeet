package com.marcosparreiras.fastfeet.domain.shipping.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.marcosparreiras.fastfeet.domain.common.boundaries.FakePasswordEncoder;
import com.marcosparreiras.fastfeet.domain.common.boundaries.InMemoryDeliveryManRepositoryTest;
import com.marcosparreiras.fastfeet.domain.common.boundaries.PasswordEncoder;
import com.marcosparreiras.fastfeet.domain.common.exceptions.InvalidCredentialsException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import com.marcosparreiras.fastfeet.domain.shipping.entities.FakeDeliveryManFactoryTest;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.resetDeliveryManPassword.ResetDeliveryManPasswordUseCase;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.resetDeliveryManPassword.ResetDeliveryManPasswordUseCaseRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResetDeliveryManPasswordUseCaseTest {

  private InMemoryDeliveryManRepositoryTest deliveryManRepository;
  private ResetDeliveryManPasswordUseCase useCase;
  private PasswordEncoder passwordEncoder;
  private String password = "123456";
  private DeliveryManEntity deliveryMan = FakeDeliveryManFactoryTest.createWithPassowrd(
    password
  );

  @BeforeEach
  void beforeEach() {
    deliveryManRepository = new InMemoryDeliveryManRepositoryTest();
    passwordEncoder = new FakePasswordEncoder();
    useCase =
      new ResetDeliveryManPasswordUseCase(
        deliveryManRepository,
        passwordEncoder
      );
    deliveryManRepository.items.add(deliveryMan);
  }

  @Test
  void shouldBeAbleToResetADeliveryManPassword() {
    try {
      String newPassword = "654321";
      ResetDeliveryManPasswordUseCaseRequest request = new ResetDeliveryManPasswordUseCaseRequest(
        deliveryMan.getId().getValue().toString(),
        password,
        newPassword
      );
      useCase.execute(request);

      DeliveryManEntity deliveryManOnRepository =
        this.deliveryManRepository.items.stream()
          .filter(dm -> dm.getId().equal(deliveryMan.getId()))
          .findFirst()
          .get();
      assertTrue(deliveryManOnRepository.validatePassword(newPassword));
    } catch (Exception e) {
      fail("Unexpected exception", e);
    }
  }

  @Test
  void shouldNotBeAbleToResetADeliveryManPasswordWihtIncorrectOldPassowrd() {
    try {
      String newPassword = "654321";
      ResetDeliveryManPasswordUseCaseRequest request = new ResetDeliveryManPasswordUseCaseRequest(
        deliveryMan.getId().getValue().toString(),
        password.concat("12"),
        newPassword
      );
      useCase.execute(request);
      fail(
        "Should not be able to reset a delivery man password with incorrect credentials"
      );
    } catch (Exception e) {
      assertThat(e).isInstanceOf(InvalidCredentialsException.class);
    }
  }
}
