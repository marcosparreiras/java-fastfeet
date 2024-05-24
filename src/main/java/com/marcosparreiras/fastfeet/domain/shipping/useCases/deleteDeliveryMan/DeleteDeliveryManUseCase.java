package com.marcosparreiras.fastfeet.domain.shipping.useCases.deleteDeliveryMan;

import com.marcosparreiras.fastfeet.domain.common.boundaries.DeliveryManRepository;
import com.marcosparreiras.fastfeet.domain.common.exceptions.DeliveryManNotFoundException;
import com.marcosparreiras.fastfeet.domain.common.exceptions.UnauthorizedException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.utils.AssertDeliveryManIsAdmin;

public class DeleteDeliveryManUseCase {

  private DeliveryManRepository deliveryManRepository;

  public DeleteDeliveryManUseCase(DeliveryManRepository deliveryManRepository) {
    this.deliveryManRepository = deliveryManRepository;
  }

  public DeleteDeliveryManUseCaseResponse execute(
    DeleteDeliveryManUseCaseRequest request
  ) throws DeliveryManNotFoundException, UnauthorizedException {
    AssertDeliveryManIsAdmin.execute(request.adminId(), deliveryManRepository);

    DeliveryManEntity deliveryMan = deliveryManRepository
      .findById(request.id())
      .orElseThrow(DeliveryManNotFoundException::new);

    deliveryManRepository.delete(deliveryMan);

    return new DeleteDeliveryManUseCaseResponse();
  }
}
