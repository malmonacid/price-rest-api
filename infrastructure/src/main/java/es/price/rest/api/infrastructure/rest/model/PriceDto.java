package es.price.rest.api.infrastructure.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
  private String productId;
  private String brandId;
  private OffsetDateTime applicationDate;
}
