package com.marcosparreiras.fastfeet.domain.shipping.useCases.updateDeliveryMan;

import com.marcosparreiras.fastfeet.domain.common.UniqueEntityId;
import com.marcosparreiras.fastfeet.domain.common.boundaries.DeliveryManRepository;
import com.marcosparreiras.fastfeet.domain.common.exceptions.DeliveryManAlreadyExistsException;
import com.marcosparreiras.fastfeet.domain.common.exceptions.DeliveryManNotFoundException;
import com.marcosparreiras.fastfeet.domain.common.exceptions.UnauthorizedException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import com.marcosparreiras.fastfeet.domain.shipping.useCases.utils.AssertDeliveryManIsAdmin;
import java.util.Optional;
import java.util.UUID;

public class UpdateDeliveryManUseCase {

  private DeliveryManRepository deliveryManRepository;

  public UpdateDeliveryManUseCase(DeliveryManRepository deliveryManRepository) {
    this.deliveryManRepository = deliveryManRepository;
  }

  public UpdateDeliveryManUseCaseResponse execute(
    UpdateDeliveryManUseCaseRequest request
  )
    throws DeliveryManNotFoundException, UnauthorizedException, DeliveryManAlreadyExistsException {
    AssertDeliveryManIsAdmin.execute(request.adminId(), deliveryManRepository);

    DeliveryManEntity deliveryMan =
      this.deliveryManRepository.findById(request.id())
        .orElseThrow(DeliveryManNotFoundException::new);

    Optional<DeliveryManEntity> isCpfAvaillable =
      this.deliveryManRepository.findByCpf(request.cpf());

    if (
      isCpfAvaillable.isPresent() &&
      !isCpfAvaillable
        .get()
        .getId()
        .equal(new UniqueEntityId(UUID.fromString(request.id())))
    ) {
      throw new DeliveryManAlreadyExistsException("Cpf already taken");
    }

    deliveryMan.setName(request.name());
    deliveryMan.setCpf(request.cpf());
    deliveryMan.setIsAdmin(request.isAdmin());

    this.deliveryManRepository.save(deliveryMan);

    return new UpdateDeliveryManUseCaseResponse(deliveryMan);
  }
}
