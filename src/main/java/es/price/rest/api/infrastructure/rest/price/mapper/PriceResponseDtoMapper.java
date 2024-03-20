package es.price.rest.api.infrastructure.rest.price.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.price.rest.api.domain.model.PriceResponse;
import es.price.rest.api.infrastructure.rest.price.dto.PriceResponseDto;

@Mapper(componentModel = "spring")
public interface PriceResponseDtoMapper {
  @Mapping(target = "productId", source = "productId")
  @Mapping(target = "brandId", source = "brandId")
  @Mapping(target = "priceList", source = "priceList")
  @Mapping(target = "startDate", source = "startDate")
  @Mapping(target = "endDate", source = "endDate")
  PriceResponseDto toDto(PriceResponse priceResponse);

}
