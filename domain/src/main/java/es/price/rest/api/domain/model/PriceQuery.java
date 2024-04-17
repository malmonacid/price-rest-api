package es.price.rest.api.domain.model;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceQuery {

  @NotNull(message = "El productId no puede ser nulo")
  private String productId;

  @NotNull
  private String brandId;

  @NotNull
  private OffsetDateTime applicationDate;
}
