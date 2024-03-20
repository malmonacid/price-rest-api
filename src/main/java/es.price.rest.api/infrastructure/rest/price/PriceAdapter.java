package es.price.rest.api.infrastructure.rest.price;

import java.time.OffsetDateTime;

import es.price.rest.api.application.ports.PricePort;
import es.price.rest.api.domain.model.PriceResponse;

public class PriceAdapter implements PricePort {
  @Override
  public PriceResponse getPrice(String productId, String brandId, OffsetDateTime applicationDate) {
    return null;
  }

}
