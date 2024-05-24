package com.marcosparreiras.fastfeet.domain.shipping.useCases.authenticateDeliveryMan;

public record AuthenticateDeliveryManUseCaseRequest(
  String cpf,
  String plainPassword
) {}
