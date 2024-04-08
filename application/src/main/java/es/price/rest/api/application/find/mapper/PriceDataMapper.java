package es.price.rest.api.application.find.mapper;

import es.price.rest.api.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.price.rest.api.domain.model.PriceOut;
import es.price.rest.api.domain.repository.model.Prices;

@Mapper(componentModel = "spring")
public interface PriceDataMapper {
  Price toData(Prices prices);

  @Mapping(target = "finalPrice", source = "price")
  PriceOut toModel(Price pricesDbData);

}
