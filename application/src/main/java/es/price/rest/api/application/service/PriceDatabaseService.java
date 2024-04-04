package es.price.rest.api.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import es.price.rest.api.application.exception.PriceDatabaseServiceException;
import es.price.rest.api.application.mapper.PriceDataMapper;
import es.price.rest.api.domain.exception.PricesDatabaseAdapterException;
import es.price.rest.api.domain.model.PriceIn;
import es.price.rest.api.domain.model.PricesData;
import es.price.rest.api.domain.ports.PricesDatabasePort;
import es.price.rest.api.domain.repository.PricesRepository;
import es.price.rest.api.domain.repository.model.Prices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceDatabaseService implements PricesDatabasePort {

  private final PricesRepository pricesRepository;

  private final PriceDataMapper priceDataMapper;

  @Override
  public PricesData findPricesByPriceRequest(PriceIn priceIn) {
    log.info("[PriceDatabaseService - findPricesByPriceRequest()] Get price with with request: {}",
        priceIn);
    try {
      Optional<Prices> optionalPrice =
          pricesRepository.findPriceByProductIdAndBrandIdAndDateByPriority(priceIn.getProductId(),
              priceIn.getBrandId(), priceIn.getApplicationDate());

      return priceDataMapper.toData(optionalPrice
          .orElseThrow(() -> new PricesDatabaseAdapterException("Empty database results")));

    } catch (RuntimeException e) {
      log.error(
          "[ERROR PriceDatabaseService - findPricesByPriceRequest()] Get price with with request: {}",
          priceIn);
      throw new PriceDatabaseServiceException(e);
    }
  }
}
