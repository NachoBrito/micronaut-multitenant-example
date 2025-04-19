package es.nachobrito.multitenant.domain.model.user;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author nacho
 */
public record UserId(String value) {
  public static UserId newRandom() {
    return new UserId(UUID.randomUUID().toString());
  }

  public @NotNull UUID toUuid() {
    return UUID.fromString(value);
  }
}
