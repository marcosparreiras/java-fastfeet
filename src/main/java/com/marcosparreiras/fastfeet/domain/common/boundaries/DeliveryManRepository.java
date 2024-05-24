package com.marcosparreiras.fastfeet.domain.common.boundaries;

import com.marcosparreiras.fastfeet.domain.shipping.entities.DeliveryManEntity;
import java.util.List;
import java.util.Optional;

public interface DeliveryManRepository {
  public Optional<DeliveryManEntity> findById(String id);

  public Optional<DeliveryManEntity> findByCpf(String cpf);

  public void save(DeliveryManEntity deliveryManEntity);

  public void delete(DeliveryManEntity deliveryManEntity);

  public List<DeliveryManEntity> findMany(int page);
}
