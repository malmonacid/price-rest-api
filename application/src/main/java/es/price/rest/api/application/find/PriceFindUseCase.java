package es.price.rest.api.application.find;

import es.price.rest.api.application.find.exception.PriceFindUseCaseException;
import es.price.rest.api.domain.ports.PricesDatabasePort;
import org.springframework.stereotype.Service;

import es.price.rest.api.application.find.mapper.PriceDataMapper;
import es.price.rest.api.domain.model.PriceQuery;
import es.price.rest.api.domain.model.PriceOut;
import es.price.rest.api.domain.ports.PricePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceFindUseCase implements PricePort {

  private final PriceDataMapper priceDataMapper;

  private final PricesDatabasePort pricesDatabasePort;

  @Override
  public PriceOut getPrice(PriceQuery priceQuery) {
    try {
      log.info("[PriceFindUseCase - getPrice()] Get price with with request: {}", priceQuery);
      return priceDataMapper.toModel(pricesDatabasePort.findPricesByPriceRequest(priceQuery));
    } catch (RuntimeException e) {
      log.error(
          "[PriceFindUseCase - getPrice()] Unexpected error in get price with with params productId: {}, brandId: {}, applicationDate: {}",
          priceQuery.getProductId(), priceQuery.getBrandId(), priceQuery.getApplicationDate());
      throw new PriceFindUseCaseException(e);
    }
  }
}
