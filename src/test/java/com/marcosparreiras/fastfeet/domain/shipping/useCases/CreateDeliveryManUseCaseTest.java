package com.marcosparreiras.fastfeet.domain.shipping.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.marcosparreiras.fastfeet.domain.common.boundaries.FakePasswordEncoder;
import com.marcosparreiras.fastfeet.domain.common.boundaries.InMemoryDeliveryManRepositoryTest;
import com.marcosparreiras.fastfeet.domain.common.boundaries.PasswordEncoder;
import com.marcosparreiras.fastfeet.domain.common.exceptions.DeliveryManAlreadyExistsException;
import com.marcosparreiras.fastfeet.domain.common.exceptions.UnauthorizedException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import com.marcosparreiras.fastfeet.domain.shipping.entities.FakeDeliveryManFactoryTest;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.createDeliveryMan.CreateDeliveryManUseCase;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.createDeliveryMan.CreateDeliveryManUseCaseRequest;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.createDeliveryMan.CreateDeliveryManUseCaseResponse;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreateDeliveryManUseCaseTest {

  private InMemoryDeliveryManRepositoryTest deliveryManRepository;
  private PasswordEncoder passwordEncoder;
  private CreateDeliveryManUseCase useCase;
  private DeliveryManEntity admin = FakeDeliveryManFactoryTest.createAdmin();
  private DeliveryManEntity deliveryMan = FakeDeliveryManFactoryTest.create();

  @BeforeEach
  void beforeEach() {
    deliveryManRepository = new InMemoryDeliveryManRepositoryTest();
    passwordEncoder = new FakePasswordEncoder();
    useCase =
      new CreateDeliveryManUseCase(deliveryManRepository, passwordEncoder);

    deliveryManRepository.items.add(admin);
    deliveryManRepository.items.add(deliveryMan);
  }

  @Test
  void nonAdminShoulNotBeAbleToCreateANewDeliveryMan() {
    try {
      String name = "John Doe";
      String cpf = "00000000000";
      String password = "123456";

      CreateDeliveryManUseCaseRequest request = new CreateDeliveryManUseCaseRequest(
        cpf,
        name,
        password,
        deliveryMan.getId().getValue().toString()
      );
      this.useCase.execute(request);
      fail("Only admin should be able to create a new delivery man");
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UnauthorizedException.class);
    }
  }

  @Test
  void anAdminShouldBeAbleToCreateANewDeliveryMan() {
    try {
      String name = "John Doe";
      String cpf = "00000000000";
      String password = "123456";

      CreateDeliveryManUseCaseRequest request = new CreateDeliveryManUseCaseRequest(
        cpf,
        name,
        password,
        admin.getId().getValue().toString()
      );
      CreateDeliveryManUseCaseResponse response = this.useCase.execute(request);
      assertThat(response.deliveryMan().getId().getValue())
        .isInstanceOf(UUID.class);

      Optional<DeliveryManEntity> deliveryManOnRepository =
        this.deliveryManRepository.items.stream()
          .filter(item -> item.getCpf() == cpf)
          .findFirst();
      if (!deliveryManOnRepository.isPresent()) {
        fail("Delivery man should be created on repository");
      }
      assertThat(deliveryManOnRepository.get().getName()).isEqualTo(name);
    } catch (Exception e) {
      fail("Unexpected exception", e);
    }
  }

  @Test
  void shouldNotBeAbleToCreateDuplicatedCPFDeliveryMan() {
    try {
      String name = "John Doe";
      String cpf = "00000000000";
      String password = "123456";

      DeliveryManEntity deliveryMan = DeliveryManEntity.create(cpf, null, null);
      this.deliveryManRepository.items.add(deliveryMan);

      CreateDeliveryManUseCaseRequest request = new CreateDeliveryManUseCaseRequest(
        cpf,
        name,
        password,
        admin.getId().getValue().toString()
      );
      this.useCase.execute(request);
      fail("Should not be able to create a delivery man with duplicated cpf");
    } catch (Exception e) {
      assertThat(e).isInstanceOf(DeliveryManAlreadyExistsException.class);
    }
  }
}
