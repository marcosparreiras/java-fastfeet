package com.marcosparreiras.fastfeet.domain.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UniqueEntityIdTest {

  @Test
  void shouldBeAbleToIdentifyEqualsIds() {
    UniqueEntityId uniqueEntityId = new UniqueEntityId();
    UniqueEntityId uniqueEntityIdClone = new UniqueEntityId(
      uniqueEntityId.getValue()
    );
    assertTrue(uniqueEntityId.equal(uniqueEntityIdClone));
  }

  @Test
  void shouldBeAbleToIdentifyUniqualIds() {
    UniqueEntityId uniqueEntityId = new UniqueEntityId();
    UniqueEntityId otherUniqueEntityId = new UniqueEntityId();
    assertFalse(uniqueEntityId.equal(otherUniqueEntityId));
  }
}
