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
public class PricesData {

  private String priceList;

  private String brandId;
  private String productId;
  private OffsetDateTime startDate;
  private int priority;
  private Double price;
  private String curr;
  private OffsetDateTime endDate;

}
