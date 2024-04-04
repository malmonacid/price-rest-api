package es.price.rest.api.infrastructure.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.PriceResponse;

import es.price.rest.api.domain.model.PriceOut;

@Mapper(componentModel = "spring")
public interface PriceResponseDtoMapper {
  @Mapping(target = "price", source = "finalPrice")
  PriceResponse toDto(PriceOut priceOut);

}
