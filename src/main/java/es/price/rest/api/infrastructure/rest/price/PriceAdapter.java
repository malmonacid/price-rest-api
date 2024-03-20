package es.price.rest.api.infrastructure.rest.price;

import org.springframework.stereotype.Component;

import es.price.rest.api.application.ports.PricePort;
import es.price.rest.api.domain.handler.exceptions.PriceAdapterException;
import es.price.rest.api.domain.model.PriceRequest;
import es.price.rest.api.domain.model.PriceResponse;
import es.price.rest.api.infrastructure.rest.price.mapper.PriceResponseDbMapper;
import es.price.rest.api.infrastructure.storage.price.PricesDatabaseAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceAdapter implements PricePort {

  private final PricesDatabaseAdapter pricesDatabaseAdapter;
  private final PriceResponseDbMapper priceResponseDbMapper;

  @Override
  public PriceResponse getPrice(PriceRequest priceRequest) {
    try {
      log.info("[PriceAdapter - getPrice()] Get price with with request: {}", priceRequest);
      return priceResponseDbMapper
          .toModel(pricesDatabaseAdapter.findPricesByPriceRequest(priceRequest));
    } catch (RuntimeException e) {
      log.error(
          "[PriceAdapter - getPrice()] Unexpected error in get price with with params productId: {}, brandId: {}, applicationDate: {}",
          priceRequest.getProductId(), priceRequest.getBrandId(),
          priceRequest.getApplicationDate());
      throw new PriceAdapterException(e);
    }
  }

}
