package es.price.rest.api.infrastructure.rest.price;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import es.price.rest.api.application.ports.PricePort;
import es.price.rest.api.domain.model.PriceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceAdapter implements PricePort {
  @Override
  public PriceResponse getPrice(String productId, String brandId, OffsetDateTime applicationDate) {
    return null;
  }

}
