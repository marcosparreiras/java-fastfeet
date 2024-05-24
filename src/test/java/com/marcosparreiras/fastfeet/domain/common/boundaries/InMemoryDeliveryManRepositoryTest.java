package com.marcosparreiras.fastfeet.domain.common.boundaries;

import com.marcosparreiras.fastfeet.domain.common.UniqueEntityId;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryDeliveryManRepositoryTest
  implements DeliveryManRepository {

  public List<DeliveryManEntity> items = new ArrayList<>();

  @Override
  public Optional<DeliveryManEntity> findById(String id) {
    UniqueEntityId uniqueEntityId = new UniqueEntityId(UUID.fromString(id));
    Optional<DeliveryManEntity> deliveryManOptional =
      this.items.stream()
        .filter(item -> item.getId().equal(uniqueEntityId))
        .findFirst();
    return deliveryManOptional;
  }

  @Override
  public Optional<DeliveryManEntity> findByCpf(String cpf) {
    Optional<DeliveryManEntity> deliveryManOptional =
      this.items.stream().filter(item -> item.getCpf() == cpf).findFirst();
    return deliveryManOptional;
  }
}
