package com.marcosparreiras.fastfeet.domain.shipping.useCases.getDeliveryMen;

import com.marcosparreiras.fastfeet.domain.common.boundaries.DeliveryManRepository;
import com.marcosparreiras.fastfeet.domain.common.exceptions.UnauthorizedException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.utils.AssertDeliveryManIsAdmin;
import java.util.List;

public class GetDeliveryMenUseCase {

  private DeliveryManRepository deliveryManRepository;

  public GetDeliveryMenUseCase(DeliveryManRepository deliveryManRepository) {
    this.deliveryManRepository = deliveryManRepository;
  }

  public GetDeliveryMenUseCaseResponse execute(
    GetDeliveryMenUseCaseRequest request
  ) throws UnauthorizedException {
    AssertDeliveryManIsAdmin.execute(request.adminId(), deliveryManRepository);

    List<DeliveryManEntity> deliveryMen =
      this.deliveryManRepository.findMany(request.page());

    return new GetDeliveryMenUseCaseResponse(deliveryMen);
  }
}
