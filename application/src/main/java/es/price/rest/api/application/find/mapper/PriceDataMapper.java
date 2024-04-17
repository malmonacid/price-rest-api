package es.price.rest.api.application.find.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.price.rest.api.domain.model.Price;
import es.price.rest.api.domain.model.PriceOut;

@Mapper(componentModel = "spring")
public interface PriceDataMapper {
  @Mapping(target = "finalPrice", source = "price")
  PriceOut toModel(Price pricesDbData);

}
