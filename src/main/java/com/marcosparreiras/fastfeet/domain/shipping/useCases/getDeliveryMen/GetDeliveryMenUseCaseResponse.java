package com.marcosparreiras.fastfeet.domain.shipping.useCases.getDeliveryMen;

import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import java.util.List;

public record GetDeliveryMenUseCaseResponse(
  List<DeliveryManEntity> deliveryMen
) {}
