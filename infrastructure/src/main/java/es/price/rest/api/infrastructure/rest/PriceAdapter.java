package es.price.rest.api.infrastructure.rest;

import org.springframework.stereotype.Component;

import es.price.rest.api.domain.exception.PriceAdapterException;
import es.price.rest.api.domain.model.PriceIn;
import es.price.rest.api.domain.model.PriceOut;
import es.price.rest.api.domain.ports.PricePort;
import es.price.rest.api.infrastructure.rest.mapper.PriceResponseDbMapper;
import es.price.rest.api.infrastructure.storage.PricesDatabaseAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceAdapter implements PricePort {

  private final PricesDatabaseAdapter pricesDatabaseAdapter;
  private final PriceResponseDbMapper priceResponseDbMapper;

  @Override
  public PriceOut getPrice(PriceIn priceIn) {
    try {
      log.info("[PriceAdapter - getPrice()] Get price with with request: {}", priceIn);
      return priceResponseDbMapper.toModel(pricesDatabaseAdapter.findPricesByPriceRequest(priceIn));
    } catch (RuntimeException e) {
      log.error(
          "[PriceAdapter - getPrice()] Unexpected error in get price with with params productId: {}, brandId: {}, applicationDate: {}",
          priceIn.getProductId(), priceIn.getBrandId(), priceIn.getApplicationDate());
      throw new PriceAdapterException(e);
    }
  }

}
