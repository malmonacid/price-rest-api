package es.price.rest.api.application.ports;

import java.time.OffsetDateTime;

import es.price.rest.api.domain.model.PriceResponse;

public interface PricePort {
  PriceResponse getPrice(String productId, String brandId, OffsetDateTime applicationDate);

}
