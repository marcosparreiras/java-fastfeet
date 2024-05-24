package com.marcosparreiras.fastfeet.domain.shipping.useCases.resetDeliveryManPassword;

import com.marcosparreiras.fastfeet.domain.common.boundaries.DeliveryManRepository;
import com.marcosparreiras.fastfeet.domain.common.boundaries.PasswordEncoder;
import com.marcosparreiras.fastfeet.domain.common.exceptions.DeliveryManNotFoundException;
import com.marcosparreiras.fastfeet.domain.common.exceptions.InvalidCredentialsException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import com.marcosparreiras.fastfeet.domain.shipping.valueObjetcts.Password;

public class ResetDeliveryManPasswordUseCase {

  private DeliveryManRepository deliveryManRepository;
  private PasswordEncoder passwordEncoder;

  public ResetDeliveryManPasswordUseCase(
    DeliveryManRepository deliveryManRepository,
    PasswordEncoder passwordEncoder
  ) {
    this.deliveryManRepository = deliveryManRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public ResetDeliveryManPasswordUseCaseResponse execute(
    ResetDeliveryManPasswordUseCaseRequest request
  ) throws DeliveryManNotFoundException, InvalidCredentialsException {
    DeliveryManEntity deliveryMan =
      this.deliveryManRepository.findById(request.deliveryManId())
        .orElseThrow(DeliveryManNotFoundException::new);

    boolean isPasswordOldPasswordValid = deliveryMan.validatePassword(
      request.oldPassword()
    );

    if (!isPasswordOldPasswordValid) {
      throw new InvalidCredentialsException();
    }

    Password password = new Password(passwordEncoder, request.newPassword());
    deliveryMan.setPassword(password);

    this.deliveryManRepository.save(deliveryMan);

    return new ResetDeliveryManPasswordUseCaseResponse();
  }
}
