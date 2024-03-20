package es.price.rest.api.infrastructure.rest.price.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceResponseDto {
  @JsonProperty("productId")
  private String productId;

  @JsonProperty("brandId")
  private String brandId;

  @JsonProperty("priceList")
  private String priceList;

  @JsonProperty("startDate")
  private OffsetDateTime startDate;

  @JsonProperty("endDate")
  private OffsetDateTime endDate;
}
