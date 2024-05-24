package com.marcosparreiras.fastfeet.domain.shipping.useCases.utils;

import com.marcosparreiras.fastfeet.domain.common.boundaries.DeliveryManRepository;
import com.marcosparreiras.fastfeet.domain.common.exceptions.UnauthorizedException;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;

public class AssertDeliveryManIsAdmin {

  public static boolean execute(
    String deliveryManId,
    DeliveryManRepository deliveryManRepository
  ) throws UnauthorizedException {
    DeliveryManEntity deliveryMan = deliveryManRepository
      .findById(deliveryManId)
      .orElseThrow(UnauthorizedException::new);

    boolean isAdmin = deliveryMan.getIsAdmin() == true;
    if (!isAdmin) {
      throw new UnauthorizedException();
    }

    return isAdmin;
  }
}
