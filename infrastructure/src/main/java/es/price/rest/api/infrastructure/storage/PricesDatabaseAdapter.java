package es.price.rest.api.infrastructure.storage;

import java.util.Optional;

import org.springframework.stereotype.Component;

import es.price.rest.api.domain.exception.PricesDatabaseAdapterException;
import es.price.rest.api.domain.model.PriceIn;
import es.price.rest.api.domain.model.PricesData;
import es.price.rest.api.domain.ports.PricesDatabasePort;
import es.price.rest.api.domain.repository.PricesRepository;
import es.price.rest.api.domain.repository.model.Prices;
import es.price.rest.api.infrastructure.storage.mapper.PricesDbDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PricesDatabaseAdapter implements PricesDatabasePort {

  private final PricesRepository pricesRepository;

  private final PricesDbDataMapper pricesDbDataMapper;

  @Override
  public PricesData findPricesByPriceRequest(PriceIn priceIn) {
    log.info("[PricesDatabaseAdapter - findPricesByPriceRequest()] Get price with with request: {}",
        priceIn);
    try {
      Optional<Prices> optionalPrice =
          pricesRepository.findPriceByProductIdAndBrandIdAndDateByPriority(priceIn.getProductId(),
              priceIn.getBrandId(), priceIn.getApplicationDate());

      return pricesDbDataMapper.toData(optionalPrice
          .orElseThrow(() -> new PricesDatabaseAdapterException("Empty database results")));

    } catch (RuntimeException e) {
      log.error(
          "[ERROR PricesDatabaseAdapter - findPricesByPriceRequest()] Get price with with request: {}",
          priceIn);
      throw new PricesDatabaseAdapterException(e);
    }
  }

}
