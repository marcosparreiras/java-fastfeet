package com.marcosparreiras.fastfeet.domain.shipping.useCases.createDeliveryMan;

public record CreateDeliveryManUseCaseRequest(
  String cpf,
  String name,
  String password,
  String adminId
) {}
