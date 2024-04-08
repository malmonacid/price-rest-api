package es.price.rest.api.domain.ports;

import es.price.rest.api.domain.model.Price;
import es.price.rest.api.domain.model.PriceQuery;

public interface PricesDatabasePort {
  Price findPricesByPriceRequest(PriceQuery priceQuery);
}
