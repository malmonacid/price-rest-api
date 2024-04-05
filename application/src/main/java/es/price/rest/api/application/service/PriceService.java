package es.price.rest.api.application.service;

import es.price.rest.api.application.exception.PriceServiceException;
import org.springframework.stereotype.Service;

import es.price.rest.api.application.mapper.PriceDataMapper;
import es.price.rest.api.domain.exception.PriceAdapterException;
import es.price.rest.api.domain.model.PriceIn;
import es.price.rest.api.domain.model.PriceOut;
import es.price.rest.api.domain.ports.PricePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceService implements PricePort {

  private final PriceDataMapper priceDataMapper;

  private final PriceDatabaseService priceDatabaseService;

  @Override
  public PriceOut getPrice(PriceIn priceIn) {
    try {
      log.info("[PriceService - getPrice()] Get price with with request: {}", priceIn);
      return priceDataMapper.toModel(priceDatabaseService.findPricesByPriceRequest(priceIn));
    } catch (RuntimeException e) {
      log.error(
          "[PriceService - getPrice()] Unexpected error in get price with with params productId: {}, brandId: {}, applicationDate: {}",
          priceIn.getProductId(), priceIn.getBrandId(), priceIn.getApplicationDate());
      throw new PriceServiceException(e);
    }
  }
}
