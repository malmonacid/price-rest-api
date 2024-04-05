package es.price.rest.api.infrastructure.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.price.rest.api.domain.model.PriceOut;
import es.price.rest.api.domain.model.PricesData;

@Mapper(componentModel = "spring")
public interface PriceResponseDbMapper {
  @Mapping(target = "finalPrice", source = "price")
  PriceOut toModel(PricesData pricesDbData);
}
