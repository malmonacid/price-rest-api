package es.price.rest.api.domain.repository.model;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
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
public class Prices {

  @Id
  private String priceList;

  @Column(nullable = false)
  private String brandId;
  @Column(nullable = false)
  private String productId;
  @Column(nullable = false)
  private OffsetDateTime startDate;
  @Column(nullable = false)
  private int priority;
  @Column(nullable = false)
  private Double price;
  @Column(nullable = false)
  private String curr;
  @Column(nullable = false)
  private OffsetDateTime endDate;

}
