package es.price.rest.api.domain.ports;

import es.price.rest.api.domain.model.PriceIn;
import es.price.rest.api.domain.model.PriceOut;

public interface PricePort {
  /**
   * Param priceRequest is formed by productId, brandId and applicationDate. * Get price for a
   * product, brand, and application date. Returns as output: product identifier, chain identifier,
   * applicable rate, application dates, and final applied price.
   *
   * @param priceIn
   * @return PriceResponse
   */
  PriceOut getPrice(PriceIn priceIn);

}
