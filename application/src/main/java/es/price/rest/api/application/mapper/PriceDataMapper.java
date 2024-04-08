package es.price.rest.api.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import es.price.rest.api.domain.model.PriceOut;
import es.price.rest.api.domain.model.PricesData;
import es.price.rest.api.domain.repository.model.Prices;

@Mapper(componentModel = "spring")
public interface PriceDataMapper {
  PricesData toData(Prices prices);

  @Mapping(target = "finalPrice", source = "price")
  PriceOut toModel(PricesData pricesDbData);

}
