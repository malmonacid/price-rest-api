package es.price.rest.api.application.ports;

import es.price.rest.api.domain.model.PriceRequest;
import es.price.rest.api.domain.model.PricesDbData;

public interface PricesDatabasePort {
  PricesDbData findPricesByPriceRequest(PriceRequest priceRequest);
}
