package com.marcosparreiras.fastfeet.domain.shipping.useCases.updateDeliveryMan;

public record UpdateDeliveryManUseCaseRequest(
  String id,
  String name,
  String cpf,
  boolean isAdmin,
  String adminId
) {}
