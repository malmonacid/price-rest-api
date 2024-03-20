package es.price.rest.api.application.ports;

import java.time.OffsetDateTime;

import es.price.rest.api.domain.model.PriceResponse;

public interface PricePort {
  /**
   * Get price for a product, brand, and application date. Returns as output: product identifier,
   * chain identifier, applicable rate, application dates, and final applied price.
   *
   * @param productId
   * @param brandId
   * @param applicationDate
   * @return PriceResponse
   */
  PriceResponse getPrice(String productId, String brandId, OffsetDateTime applicationDate);

}
