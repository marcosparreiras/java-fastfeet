package com.marcosparreiras.fastfeet.domain.shipping.useCases.createDeliveryMan;

import com.marcosparreiras.fastfeet.domain.common.boundaries.DeliveryManRepository;
import com.marcosparreiras.fastfeet.domain.common.boundaries.PasswordEncoder;
import com.marcosparreiras.fastfeet.domain.common.exceptions.DeliveryManAlreadyExistsException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import com.marcosparreiras.fastfeet.domain.shipping.valueObjetcts.Password;
import java.util.Optional;

public class CreateDeliveryManUseCase {

  private DeliveryManRepository deliveryManRepository;
  private PasswordEncoder passwordEncoder;

  public CreateDeliveryManUseCase(
    DeliveryManRepository deliveryManRepository,
    PasswordEncoder passwordEncoder
  ) {
    this.deliveryManRepository = deliveryManRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public CreateDeliveryManUseCaseResponse execute(
    CreateDeliveryManUseCaseRequest request
  ) throws DeliveryManAlreadyExistsException {
    Optional<DeliveryManEntity> deliveryManExists =
      this.deliveryManRepository.findByCpf(request.cpf());
    if (deliveryManExists.isPresent()) {
      throw new DeliveryManAlreadyExistsException();
    }

    Password password = new Password(passwordEncoder, request.password());
    DeliveryManEntity deliveryMan = DeliveryManEntity.create(
      request.cpf(),
      request.name(),
      password
    );

    this.deliveryManRepository.save(deliveryMan);

    return new CreateDeliveryManUseCaseResponse(deliveryMan);
  }
}
