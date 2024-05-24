package com.marcosparreiras.fastfeet.domain.common.boundaries;

import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import java.util.Optional;

public interface DeliveryManRepository {
  public Optional<DeliveryManEntity> findById(String id);

  public Optional<DeliveryManEntity> findByCpf(String cpf);

  public void save(DeliveryManEntity deliveryManEntity);
}
