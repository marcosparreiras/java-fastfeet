package com.marcosparreiras.fastfeet.domain.shipping.useCases.resetDeliveryManPassword;

public record ResetDeliveryManPasswordUseCaseRequest(
  String deliveryManId,
  String oldPassword,
  String newPassword
) {}
