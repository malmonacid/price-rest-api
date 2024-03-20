package es.price.rest.api.infrastructure.storage.price.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceEntity {

  @Id
  private String priceList;

  private String brandId;
  private String productId;
  private OffsetDateTime startDate;
  private int priority;
  private Double price;
  private String curr;
  private OffsetDateTime endDate;

}
