package es.price.rest.api.infrastructure.storage.mapper;

import org.mapstruct.Mapper;

import es.price.rest.api.domain.model.Price;
import es.price.rest.api.domain.repository.model.Prices;

@Mapper(componentModel = "spring")
public interface PricesDbDataMapper {
  Price toData(Prices prices);
}
