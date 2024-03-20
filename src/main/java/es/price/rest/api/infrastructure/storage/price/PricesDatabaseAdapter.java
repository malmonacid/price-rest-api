package es.price.rest.api.infrastructure.storage.price;

import java.util.Optional;

import org.springframework.stereotype.Component;

import es.price.rest.api.application.ports.PricesDatabasePort;
import es.price.rest.api.domain.handler.exceptions.PricesDatabaseAdapterException;
import es.price.rest.api.domain.model.PriceRequest;
import es.price.rest.api.domain.model.PricesDbData;
import es.price.rest.api.infrastructure.storage.price.mapper.PricesDbDataMapper;
import es.price.rest.api.infrastructure.storage.price.model.PriceEntity;
import es.price.rest.api.infrastructure.storage.price.repository.PricesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PricesDatabaseAdapter implements PricesDatabasePort {

  private final PricesRepository pricesRepository;

  private final PricesDbDataMapper pricesDbDataMapper;

  @Override
  public PricesDbData findPricesByPriceRequest(PriceRequest priceRequest) {
    log.info("[PricesDatabaseAdapter - findPricesByPriceRequest()] Get price with with request: {}",
        priceRequest);
    try {
      Optional<PriceEntity> optionalPrice = pricesRepository
          .findPriceByProductIdAndBrandIdAndDateByPriority(priceRequest.getProductId(),
              priceRequest.getBrandId(), priceRequest.getApplicationDate());

      return pricesDbDataMapper.toData(optionalPrice
          .orElseThrow(() -> new PricesDatabaseAdapterException("Empty database results")));

    } catch (RuntimeException e) {
      log.error(
          "[ERROR PricesDatabaseAdapter - findPricesByPriceRequest()] Get price with with request: {}",
          priceRequest);
      throw new PricesDatabaseAdapterException(e);
    }
  }

}
