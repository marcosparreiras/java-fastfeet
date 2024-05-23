package com.marcosparreiras.fastfeet.domain.shipping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.marcosparreiras.fastfeet.domain.common.UniqueEntityId;
import com.marcosparreiras.fastfeet.domain.shipping.entities.RemitteeEntity;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RemitteeEntittyTest {

  @Test
  void shouldBeAbleToCreateANewRemittee() {
    String cpf = "00000000000";
    String name = "John Doe";
    RemitteeEntity remittee = RemitteeEntity.create(cpf, name);
    assertThat(remittee.getName()).isEqualTo(name);
    assertThat(remittee.getCpf()).isEqualTo(cpf);
    assertThat(remittee.getId()).isNotNull();
    assertThat(remittee.getUpdatedAt()).isNull();

    remittee.setName("John Doe Hass");
    assertThat(remittee.getUpdatedAt()).isNotNull();
  }

  @Test
  void shouldBeAbleToLoadAnExistentRemittee() {
    UniqueEntityId id = new UniqueEntityId();
    String cpf = "00000000000";
    String name = "John Doe";
    LocalDate createdAt = LocalDate.now();
    LocalDate updatedAt = null;
    RemitteeEntity remittee = RemitteeEntity.load(
      id,
      cpf,
      name,
      createdAt,
      updatedAt
    );

    assertTrue(remittee.getId().equal(id));
    assertThat(remittee.getName()).isEqualTo(name);
    assertThat(remittee.getCpf()).isEqualTo(cpf);
    assertThat(remittee.getCreatedAt()).isEqualTo(createdAt);
    assertThat(remittee.getUpdatedAt()).isNull();

    remittee.setName("John Doe Hass");
    assertThat(remittee.getUpdatedAt()).isNotNull();
  }
}
