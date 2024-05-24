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
    AuthenticateDeliveryManUseCaseRequest authenticateDeliveryManUseCaseRequest
  ) throws InvalidCredentialsException {
    String cpf = authenticateDeliveryManUseCaseRequest.cpf();
    String plainPassword = authenticateDeliveryManUseCaseRequest.plainPassword();

    DeliveryManEntity deliveryMan =
      this.deliveryManRepository.findByCpf(cpf)
        .orElseThrow(InvalidCredentialsException::new);

    boolean passwordMatch = deliveryMan.validatePassword(plainPassword);
    if (!passwordMatch) {
      throw new InvalidCredentialsException();
    }
    return new AuthenticateDeliveryManUseCaseResponse(deliveryMan);
  }
}
