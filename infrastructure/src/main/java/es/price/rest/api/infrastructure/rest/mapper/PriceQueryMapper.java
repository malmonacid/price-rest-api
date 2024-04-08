package es.price.rest.api.infrastructure.rest.mapper;

import es.price.rest.api.domain.model.PriceQuery;
import es.price.rest.api.infrastructure.rest.model.PriceDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceQueryMapper {
    PriceQuery toQuery(PriceDto priceOut);
}
