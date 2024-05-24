package com.marcosparreiras.fastfeet.domain.shipping.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.marcosparreiras.fastfeet.domain.common.boundaries.InMemoryDeliveryManRepositoryTest;
import com.marcosparreiras.fastfeet.domain.common.exceptions.DeliveryManNotFoundException;
import com.marcosparreiras.fastfeet.domain.common.exceptions.UnauthorizedException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import com.marcosparreiras.fastfeet.domain.shipping.entities.FakeDeliveryManFactoryTest;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.deleteDeliveryMan.DeleteDeliveryManUseCase;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.deleteDeliveryMan.DeleteDeliveryManUseCaseRequest;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.deleteDeliveryMan.DeleteDeliveryManUseCaseResponse;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeleteDeliveryManUseCaseTest {

  private InMemoryDeliveryManRepositoryTest deliveryManRepository;
  private DeleteDeliveryManUseCase useCase;
  private DeliveryManEntity admin = FakeDeliveryManFactoryTest.createAdmin();
  private DeliveryManEntity deliveryMan = FakeDeliveryManFactoryTest.create();

  @BeforeEach
  void beforeEach() {
    deliveryManRepository = new InMemoryDeliveryManRepositoryTest();
    useCase = new DeleteDeliveryManUseCase(deliveryManRepository);
    deliveryManRepository.items.add(admin);
    deliveryManRepository.items.add(deliveryMan);
  }

  @Test
  void anAdminShouldBeAbleToDeleteADeliveryMan() {
    try {
      DeleteDeliveryManUseCaseRequest request = new DeleteDeliveryManUseCaseRequest(
        deliveryMan.getId().getValue().toString(),
        admin.getId().getValue().toString()
      );
      DeleteDeliveryManUseCaseResponse response = useCase.execute(request);
      assertThat(response).isInstanceOf(DeleteDeliveryManUseCaseResponse.class);
    } catch (Exception e) {
      fail("Unexpected exception", e);
    }
  }

  @Test
  void shouldNotBeAbleToDeleteAnUnexistentDeliveryMan() {
    try {
      DeleteDeliveryManUseCaseRequest request = new DeleteDeliveryManUseCaseRequest(
        UUID.randomUUID().toString(),
        admin.getId().getValue().toString()
      );
      useCase.execute(request);
      fail("Should not be able to delete an unexistent delivery man");
    } catch (Exception e) {
      assertThat(e).isInstanceOf(DeliveryManNotFoundException.class);
    }
  }

  @Test
  void onlyAdminShouldBeAbleToDeleteADelivveryMan() {
    try {
      DeleteDeliveryManUseCaseRequest request = new DeleteDeliveryManUseCaseRequest(
        admin.getId().getValue().toString(),
        deliveryMan.getId().getValue().toString()
      );
      useCase.execute(request);
      fail("Only an admin should be able to delete a delivery man");
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UnauthorizedException.class);
    }
  }
}
