package es.price.rest.api.infrastructure.db;

import java.util.Optional;

import org.springframework.stereotype.Component;

import es.price.rest.api.domain.exception.PricesDatabaseAdapterException;
import es.price.rest.api.domain.exception.ProductNotFoundException;
import es.price.rest.api.domain.model.Price;
import es.price.rest.api.domain.model.PriceQuery;
import es.price.rest.api.domain.ports.PricesDatabasePort;
import es.price.rest.api.infrastructure.db.mapper.PricesDbDataMapper;
import es.price.rest.api.infrastructure.db.repository.PricesRepository;
import es.price.rest.api.infrastructure.db.repository.model.Prices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PricesDatabaseAdapter implements PricesDatabasePort {

  private final PricesRepository pricesRepository;

  private final PricesDbDataMapper pricesDbDataMapper;

  @Override
  public Price findPricesByPriceRequest(@Valid PriceQuery priceQuery) {
    log.info("[PricesDatabaseAdapter - findPricesByPriceRequest()] Get price with with request: {}",
        priceQuery);
    try {
      Optional<Prices> optionalPrice = pricesRepository.findPricesByProductIdAndBrandIdAndDate(
          priceQuery.getProductId(), priceQuery.getBrandId(), priceQuery.getApplicationDate());

      return pricesDbDataMapper.toData(
          optionalPrice.orElseThrow(() -> new ProductNotFoundException("Product not found")));

    } catch (RuntimeException e) {
      log.error(
          "[ERROR PricesDatabaseAdapter - findPricesByPriceRequest()] Get price with with request: {}",
          priceQuery);
      throw new PricesDatabaseAdapterException(e);
    }
  }

}
