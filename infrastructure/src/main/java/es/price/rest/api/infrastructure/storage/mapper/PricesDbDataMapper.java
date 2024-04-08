package es.price.rest.api.infrastructure.storage.mapper;

import org.mapstruct.Mapper;

import es.price.rest.api.domain.model.PricesData;
import es.price.rest.api.domain.repository.model.Prices;
import es.price.rest.api.infrastructure.storage.model.PricesDto;

@Mapper(componentModel = "spring")
public interface PricesDbDataMapper {
  PricesData toData(Prices prices);

  PricesDto toDto(Prices prices);

  PricesData toDataDto(PricesDto pricesDto);
}
