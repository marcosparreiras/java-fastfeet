package com.marcosparreiras.fastfeet.domain.common.boundaries;

import com.marcosparreiras.fastfeet.domain.common.UniqueEntityId;
import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryDeliveryManRepositoryTest
  implements DeliveryManRepository {

  private final int pageSize = 10;

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

  @Override
  public void save(DeliveryManEntity deliveryManEntity) {
    int index = this.items.indexOf(deliveryManEntity);
    if (index < 0) {
      this.items.add(deliveryManEntity);
      return;
    }
    this.items.set(index, deliveryManEntity);
  }

  @Override
  public List<DeliveryManEntity> findMany(int page) {
    if (page <= 0) {
      return new ArrayList<DeliveryManEntity>();
    }
    List<DeliveryManEntity> deliveryMenOrderByCreatedAtDesc =
      this.items.stream()
        .sorted(Comparator.comparing(DeliveryManEntity::getCreatedAt))
        .toList();
    int startIndex = (page - 1) * pageSize;
    int endIndex = page * pageSize;
    if (startIndex >= deliveryMenOrderByCreatedAtDesc.size()) {
      return new ArrayList<DeliveryManEntity>();
    }

    if (endIndex > deliveryMenOrderByCreatedAtDesc.size()) {
      return deliveryMenOrderByCreatedAtDesc.subList(
        startIndex,
        deliveryMenOrderByCreatedAtDesc.size()
      );
    }
    return deliveryMenOrderByCreatedAtDesc.subList(startIndex, endIndex);
  }

  @Override
  public void delete(DeliveryManEntity deliveryManEntity) {
    int index = this.items.indexOf(deliveryManEntity);
    this.items.remove(index);
  }
}
