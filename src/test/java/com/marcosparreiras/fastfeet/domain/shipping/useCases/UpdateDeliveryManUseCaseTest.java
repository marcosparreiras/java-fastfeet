package com.marcosparreiras.fastfeet.domain.shipping.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.marcosparreiras.fastfeet.domain.common.boundaries.InMemoryDeliveryManRepositoryTest;
import com.marcosparreiras.fastfeet.domain.common.exceptions.DeliveryManAlreadyExistsException;
import com.marcosparreiras.fastfeet.domain.common.exceptions.UnauthorizedException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import com.marcosparreiras.fastfeet.domain.shipping.entities.FakeDeliveryManFactoryTest;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.updateDeliveryMan.UpdateDeliveryManUseCase;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.updateDeliveryMan.UpdateDeliveryManUseCaseRequest;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.updateDeliveryMan.UpdateDeliveryManUseCaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UpdateDeliveryManUseCaseTest {

  private InMemoryDeliveryManRepositoryTest deliveryManRepository;
  private UpdateDeliveryManUseCase useCase;
  private DeliveryManEntity admin = FakeDeliveryManFactoryTest.createAdmin();
  private DeliveryManEntity deliveryMan = FakeDeliveryManFactoryTest.create();

  @BeforeEach
  void beforeEach() {
    deliveryManRepository = new InMemoryDeliveryManRepositoryTest();
    useCase = new UpdateDeliveryManUseCase(deliveryManRepository);
    deliveryManRepository.items.add(admin);
    deliveryManRepository.items.add(deliveryMan);
  }

  @Test
  void anAdminShouldBeAbleToUpdateADeliveryMan() {
    try {
      String newName = "John Doe";
      String newCpf = "00000000000";
      boolean isAdmin = true;

      UpdateDeliveryManUseCaseRequest request = new UpdateDeliveryManUseCaseRequest(
        deliveryMan.getId().getValue().toString(),
        newName,
        newCpf,
        isAdmin,
        admin.getId().getValue().toString()
      );

      UpdateDeliveryManUseCaseResponse response = useCase.execute(request);

      DeliveryManEntity updatedDeliveryMan = response.deliveryMan();
      assertTrue(updatedDeliveryMan.getId().equal(deliveryMan.getId()));
      assertThat(updatedDeliveryMan.getName()).isEqualTo(newName);
      assertThat(updatedDeliveryMan.getCpf()).isEqualTo(newCpf);
      assertThat(updatedDeliveryMan.getIsAdmin()).isEqualTo(isAdmin);

      DeliveryManEntity deliveryManOnRepository =
        this.deliveryManRepository.items.stream()
          .filter(dm -> dm.getId().equal(updatedDeliveryMan.getId()))
          .findFirst()
          .get();

      assertThat(deliveryManOnRepository.getName()).isEqualTo(newName);
      assertThat(deliveryManOnRepository.getCpf()).isEqualTo(newCpf);
      assertThat(deliveryManOnRepository.getIsAdmin()).isEqualTo(isAdmin);
    } catch (Exception e) {
      fail("Unexpected exception", e);
    }
  }

  @Test
  void shouldBeAbleToUpdateADeliveryManAndPreserveHisCpf() {
    try {
      String newName = "John Doe";
      boolean isAdmin = true;

      UpdateDeliveryManUseCaseRequest request = new UpdateDeliveryManUseCaseRequest(
        deliveryMan.getId().getValue().toString(),
        newName,
        deliveryMan.getCpf(),
        isAdmin,
        admin.getId().getValue().toString()
      );

      UpdateDeliveryManUseCaseResponse response = useCase.execute(request);
      DeliveryManEntity updatedDeliveryMan = response.deliveryMan();
      assertTrue(updatedDeliveryMan.getId().equal(deliveryMan.getId()));
    } catch (Exception e) {
      fail("unexpected exception", e);
    }
  }

  @Test
  void shouldNotBeAbleToUpdateADeliveryManWithAnotherDeliveryManCpf() {
    try {
      DeliveryManEntity anotherDeliveryMan = FakeDeliveryManFactoryTest.create();
      this.deliveryManRepository.items.add(anotherDeliveryMan);

      String newName = "John Doe";
      String newCpf = anotherDeliveryMan.getCpf();
      boolean isAdmin = true;

      UpdateDeliveryManUseCaseRequest request = new UpdateDeliveryManUseCaseRequest(
        deliveryMan.getId().getValue().toString(),
        newName,
        newCpf,
        isAdmin,
        admin.getId().getValue().toString()
      );

      useCase.execute(request);
      fail(
        "Should not be able to update a delivery man cpf with a cpf of another delivery man"
      );
    } catch (Exception e) {
      assertThat(e).isInstanceOf(DeliveryManAlreadyExistsException.class);
    }
  }

  @Test
  void onlyAdminShouldBeAbleToUpdateADeliveryMan() {
    try {
      String newName = "John Doe";
      String newCpf = "00000000000";
      boolean isAdmin = true;

      UpdateDeliveryManUseCaseRequest request = new UpdateDeliveryManUseCaseRequest(
        deliveryMan.getId().getValue().toString(),
        newName,
        newCpf,
        isAdmin,
        deliveryMan.getId().getValue().toString()
      );

      useCase.execute(request);
      fail("Only Admin should not be able to update a delivery man");
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UnauthorizedException.class);
    }
  }
}
