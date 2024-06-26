package es.price.rest.api.infrastructure.rest.controller;

import java.time.OffsetDateTime;

import org.openapitools.api.PriceApi;
import org.openapitools.model.PriceResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.price.rest.api.domain.ports.PriceFindUseCasePort;
import es.price.rest.api.infrastructure.rest.mapper.PriceQueryMapper;
import es.price.rest.api.infrastructure.rest.mapper.PriceResponseDtoMapper;
import es.price.rest.api.infrastructure.rest.model.PriceDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping()
@AllArgsConstructor
@Slf4j
@Validated
public class PriceController implements PriceApi {

  private final PriceFindUseCasePort priceFindUseCasePort;
  private final PriceResponseDtoMapper priceResponseDtoMapper;
  private final PriceQueryMapper priceQueryMapper;

  @Override
  @GetMapping(value = "/prices", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PriceResponse> getPrice(@RequestParam OffsetDateTime applicationDate,
      @RequestParam String productId, @RequestParam String brandId) {
    log.info(
        "[PriceController - /price] Get price with params: productId: {}, brandId: {}, applicationDate: {}",
        productId, brandId, applicationDate);
    return ResponseEntity.ok(priceResponseDtoMapper
        .toDto(priceFindUseCasePort.getPrice(priceQueryMapper.toQuery(PriceDto.builder()
            .productId(productId).brandId(brandId).applicationDate(applicationDate).build()))));
  }
}
