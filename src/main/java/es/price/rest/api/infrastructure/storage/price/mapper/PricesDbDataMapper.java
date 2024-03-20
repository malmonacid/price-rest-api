package es.price.rest.api.infrastructure.storage.price.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.price.rest.api.domain.model.PricesDbData;
import es.price.rest.api.infrastructure.storage.price.model.Prices;

@Mapper(componentModel = "spring")
public interface PricesDbDataMapper {
  @Mapping(target = "priceList", source = "priceList")
  @Mapping(target = "brandId", source = "brandId")
  @Mapping(target = "productId", source = "productId")
  @Mapping(target = "startDate", source = "startDate")
  @Mapping(target = "priority", source = "priority")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "curr", source = "curr")
  @Mapping(target = "endDate", source = "endDate")
  PricesDbData toData(Prices priceResponse);
}
