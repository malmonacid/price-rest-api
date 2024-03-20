package es.price.rest.api.infrastructure.storage.price;

import org.springframework.stereotype.Component;

import es.price.rest.api.application.ports.PricesDatabasePort;
import es.price.rest.api.domain.model.PriceRequest;
import es.price.rest.api.domain.model.PricesDbData;
import es.price.rest.api.infrastructure.storage.price.mapper.PricesDbDataMapper;
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
    return null;
  }

}
