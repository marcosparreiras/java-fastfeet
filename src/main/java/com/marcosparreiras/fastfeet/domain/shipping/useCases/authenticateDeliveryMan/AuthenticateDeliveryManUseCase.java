package com.marcosparreiras.fastfeet.domain.shipping.useCases.authenticateDeliveryMan;

import com.marcosparreiras.fastfeet.domain.common.boundaries.DeliveryManRepository;
import com.marcosparreiras.fastfeet.domain.common.exceptions.InvalidCredentialsException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;

public class AuthenticateDeliveryManUseCase {

  DeliveryManRepository deliveryManRepository;

  public AuthenticateDeliveryManUseCase(
    DeliveryManRepository deliveryManRepository
  ) {
    this.deliveryManRepository = deliveryManRepository;
  }

  public AuthenticateDeliveryManUseCaseResponse execute(
    AuthenticateDeliveryManUseCaseRequest request
  ) throws InvalidCredentialsException {
    DeliveryManEntity deliveryMan =
      this.deliveryManRepository.findByCpf(request.cpf())
        .orElseThrow(InvalidCredentialsException::new);

    boolean passwordMatch = deliveryMan.validatePassword(
      request.plainPassword()
    );
    if (!passwordMatch) {
      throw new InvalidCredentialsException();
    }

    return new AuthenticateDeliveryManUseCaseResponse(deliveryMan);
  }
}
