package es.price.rest.api.domain.ports;

import es.price.rest.api.domain.model.PriceIn;
import es.price.rest.api.domain.model.PricesData;

public interface PricesDatabasePort {
  PricesData findPricesByPriceRequest(PriceIn priceIn);
}
