package es.price.rest.api.domain.model;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceOut {
  private String productId;
  private String brandId;
  private String priceList;
  private OffsetDateTime startDate;
  private OffsetDateTime endDate;
  private Double finalPrice;
}
