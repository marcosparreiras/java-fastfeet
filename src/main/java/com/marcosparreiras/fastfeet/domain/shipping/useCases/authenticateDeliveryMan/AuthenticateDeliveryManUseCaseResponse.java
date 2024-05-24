package com.marcosparreiras.fastfeet.domain.shipping.useCases.authenticateDeliveryMan;

import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;

public record AuthenticateDeliveryManUseCaseResponse(
  DeliveryManEntity deliveryManEntity
) {}
