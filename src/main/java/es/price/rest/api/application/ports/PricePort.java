package es.price.rest.api.application.ports;

import es.price.rest.api.domain.model.PriceRequest;
import es.price.rest.api.domain.model.PriceResponse;

public interface PricePort {
  /**
   * Param priceRequest is formed by productId, brandId and applicationDate. * Get price for a
   * product, brand, and application date. Returns as output: product identifier, chain identifier,
   * applicable rate, application dates, and final applied price.
   *
   * @param priceRequest
   * @return PriceResponse
   */
  PriceResponse getPrice(PriceRequest priceRequest);

}
