package es.price.rest.api.interfaces.rest;

import java.time.OffsetDateTime;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.price.rest.api.application.ports.PricePort;
import es.price.rest.api.domain.model.PriceRequest;
import es.price.rest.api.infrastructure.rest.price.dto.PriceResponseDto;
import es.price.rest.api.infrastructure.rest.price.mapper.PriceResponseDtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping()
@AllArgsConstructor
@Slf4j
@Validated
public class PriceController {

  private final PricePort pricePort;
  private final PriceResponseDtoMapper priceResponseDtoMapper;

  @GetMapping(value = "/price", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PriceResponseDto> getPrice(@RequestParam String productId,
      @RequestParam String brandId, @RequestParam OffsetDateTime applicationDate) {
    log.info(
        "[PriceController - /price] Get price with params: productId: {}, brandId: {}, applicationDate: {}",
        productId, brandId, applicationDate);
    return ResponseEntity.ok(priceResponseDtoMapper.toDto(pricePort.getPrice(PriceRequest.builder()
        .productId(productId).brandId(brandId).applicationDate(applicationDate).build())));
  }

}
