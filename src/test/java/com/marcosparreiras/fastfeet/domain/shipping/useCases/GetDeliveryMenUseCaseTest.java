package com.marcosparreiras.fastfeet.domain.shipping.useCases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import com.marcosparreiras.fastfeet.domain.common.boundaries.InMemoryDeliveryManRepositoryTest;
import com.marcosparreiras.fastfeet.domain.common.exceptions.UnauthorizedException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import com.marcosparreiras.fastfeet.domain.shipping.entities.FakeDeliveryManFactoryTest;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.getDeliveryMen.GetDeliveryMenUseCase;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.getDeliveryMen.GetDeliveryMenUseCaseRequest;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.getDeliveryMen.GetDeliveryMenUseCaseResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetDeliveryMenUseCaseTest {

  private InMemoryDeliveryManRepositoryTest deliveryManRepository;
  private GetDeliveryMenUseCase useCase;
  private DeliveryManEntity admin = FakeDeliveryManFactoryTest.createAdmin();
  private DeliveryManEntity deliveryman = FakeDeliveryManFactoryTest.create();

  @BeforeEach
  void beforeEach() {
    deliveryManRepository = new InMemoryDeliveryManRepositoryTest();
    useCase = new GetDeliveryMenUseCase(deliveryManRepository);
    deliveryManRepository.items.add(admin);
    deliveryManRepository.items.add(deliveryman);
  }

  @Test
  void anAdminShouldBeAbleToFetchDeliveryMen() {
    try {
      GetDeliveryMenUseCaseRequest request = new GetDeliveryMenUseCaseRequest(
        1,
        admin.getId().getValue().toString()
      );
      GetDeliveryMenUseCaseResponse response = useCase.execute(request);
      List<DeliveryManEntity> deliveryMen = response.deliveryMen();

      assertThat(deliveryMen.size()).isEqualTo(2);
      assertThat(deliveryMen.get(0)).isInstanceOf(DeliveryManEntity.class);
    } catch (Exception e) {
      fail("Unexpected exception", e);
    }
  }

  @Test
  void onlyAdminShouldBeAbleToFetchDeliveryMan() {
    try {
      GetDeliveryMenUseCaseRequest request = new GetDeliveryMenUseCaseRequest(
        1,
        deliveryman.getId().getValue().toString()
      );
      useCase.execute(request);
      fail("Only admin should be able to fetch delivery men");
    } catch (Exception e) {
      assertThat(e).isInstanceOf(UnauthorizedException.class);
    }
  }

  @Test
  void anAdminShouldBeAbleToFetchDeliveryMenByPage() {
    try {
      for (int i = 1; i <= 10; i++) {
        DeliveryManEntity dm = FakeDeliveryManFactoryTest.create();
        deliveryManRepository.items.add(dm);
      }

      GetDeliveryMenUseCaseRequest request01 = new GetDeliveryMenUseCaseRequest(
        1,
        admin.getId().getValue().toString()
      );
      GetDeliveryMenUseCaseResponse response01 = useCase.execute(request01);

      GetDeliveryMenUseCaseRequest request02 = new GetDeliveryMenUseCaseRequest(
        2,
        admin.getId().getValue().toString()
      );
      GetDeliveryMenUseCaseResponse response02 = useCase.execute(request02);

      assertThat(response01.deliveryMen().size()).isEqualTo(10);
      assertThat(response02.deliveryMen().size()).isEqualTo(2);
    } catch (Exception e) {
      fail("Unexpected exception", e);
    }
  }
}
