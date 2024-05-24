package com.marcosparreiras.fastfeet.domain.shipping.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.marcosparreiras.fastfeet.domain.common.boundaries.InMemoryDeliveryManRepositoryTest;
import com.marcosparreiras.fastfeet.domain.common.exceptions.InvalidCredentialsException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import com.marcosparreiras.fastfeet.domain.shipping.entities.FakeDeliveryManFactoryTest;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.authenticateDeliveryMan.AuthenticateDeliveryManUseCase;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.authenticateDeliveryMan.AuthenticateDeliveryManUseCaseRequest;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.authenticateDeliveryMan.AuthenticateDeliveryManUseCaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthenticateDeliveryManUseCaseTest {

  private InMemoryDeliveryManRepositoryTest deliveryManRepository;
  private AuthenticateDeliveryManUseCase useCase;

  private String plainPassword = "123456";
  private DeliveryManEntity deliveryMan = FakeDeliveryManFactoryTest.createWithPassowrd(
    plainPassword
  );

  @BeforeEach
  void beforeEach() {
    deliveryManRepository = new InMemoryDeliveryManRepositoryTest();
    useCase = new AuthenticateDeliveryManUseCase(deliveryManRepository);
    deliveryManRepository.items.add(deliveryMan);
  }

  @Test
  void shouldBeAbleToAuthenticateADeliveryManWithCorrectCredentials() {
    try {
      AuthenticateDeliveryManUseCaseRequest request = new AuthenticateDeliveryManUseCaseRequest(
        deliveryMan.getCpf(),
        plainPassword
      );
      AuthenticateDeliveryManUseCaseResponse response = useCase.execute(
        request
      );
      DeliveryManEntity deliveryManEntity = response.deliveryManEntity();
      assertTrue(deliveryManEntity.getId().equal(deliveryManEntity.getId()));
    } catch (Exception e) {
      fail("Unexpected exception", e);
    }
  }

  @Test
  void shouldNotBeAbleToAuthenticateDeliveryManWithIncorrectPassword() {
    try {
      AuthenticateDeliveryManUseCaseRequest request = new AuthenticateDeliveryManUseCaseRequest(
        deliveryMan.getCpf(),
        plainPassword.concat("54")
      );
      this.useCase.execute(request);
      fail("Should not authenticate delivery man with incorrect credentials");
    } catch (Exception e) {
      assertThat(e).isInstanceOf(InvalidCredentialsException.class);
    }
  }

  @Test
  void shouldNotBeAbleToAuthenticateDeliveryManWithUnexistentCpf() {
    try {
      AuthenticateDeliveryManUseCaseRequest request = new AuthenticateDeliveryManUseCaseRequest(
        deliveryMan.getCpf().substring(2).concat("54"),
        plainPassword
      );
      useCase.execute(request);
      fail("Should not authenticate delivery man with incorrect credentials");
    } catch (Exception e) {
      assertThat(e).isInstanceOf(InvalidCredentialsException.class);
    }
  }
}
