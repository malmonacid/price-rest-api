package es.price.rest.api.domain.ports;

import es.price.rest.api.domain.model.PriceQuery;
import es.price.rest.api.domain.model.PriceOut;

public interface PricePort {
  /**
   * Param priceRequest is formed by productId, brandId and applicationDate. * Get price for a
   * product, brand, and application date. Returns as output: product identifier, chain identifier,
   * applicable rate, application dates, and final applied price.
   *
   * @param priceQuery
   * @return PriceResponse
   */
  PriceOut getPrice(PriceQuery priceQuery);

}
