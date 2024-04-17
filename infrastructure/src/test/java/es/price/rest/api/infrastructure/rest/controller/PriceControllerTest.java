package es.price.rest.api.infrastructure.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.openapitools.model.PriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.price.rest.api.ApplicationTestUtils;
import es.price.rest.api.domain.model.PriceOut;
import es.price.rest.api.domain.model.PriceQuery;
import es.price.rest.api.domain.ports.PriceFindUseCasePort;
import es.price.rest.api.infrastructure.rest.mapper.PriceQueryMapper;
import es.price.rest.api.infrastructure.rest.mapper.PriceQueryMapperImpl;
import es.price.rest.api.infrastructure.rest.mapper.PriceResponseDtoMapper;
import es.price.rest.api.infrastructure.rest.mapper.PriceResponseDtoMapperImpl;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
@ContextConfiguration(classes = {PriceResponseDtoMapperImpl.class, PriceQueryMapperImpl.class})
public class PriceControllerTest extends ApplicationTestUtils {

  private PriceController priceController;
  @Mock
  private PriceFindUseCasePort priceFindUseCasePort;

  @Autowired
  PriceResponseDtoMapper priceResponseDtoMapper;

  @Autowired
  PriceQueryMapper priceQueryMapper;

  @BeforeEach
  void setUp() {
    priceController =
        new PriceController(priceFindUseCasePort, priceResponseDtoMapper, priceQueryMapper);
  }

  @Test
  void givenAnOkQueryParams_whenCallingPricesEndpointWithCorrectParams_thenReturnOkResponse(
      CapturedOutput output) throws IOException {
    // arrange
    PriceQuery priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceQuery.class);
    PriceOut priceResponse = createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceOut.class);

    when(priceFindUseCasePort.getPrice(priceRequest)).thenReturn(priceResponse);

    // act
    ResponseEntity<PriceResponse> response = priceController.getPrice(
        priceRequest.getApplicationDate(), priceRequest.getProductId(), priceRequest.getBrandId());

    // assert
    assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    assertThat(output).contains("[PriceController - /price] Get price with params");
  }

  @Test
  void givenAnOkQueryParams_whenCallingPricesEndpointWithGivenParams_thenReturnOkResponseAndLogParams(
      CapturedOutput output) throws IOException {
    // arrange
    PriceQuery priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceQuery.class);
    PriceOut priceResponse = createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceOut.class);

    when(priceFindUseCasePort.getPrice(priceRequest)).thenReturn(priceResponse);

    // act
    ResponseEntity<PriceResponse> response = priceController.getPrice(
        priceRequest.getApplicationDate(), priceRequest.getProductId(), priceRequest.getBrandId());

    // assert
    assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    assertThat(output).contains(
        "[PriceController - /price] Get price with params: productId: 35455, brandId: 1, applicationDate: 2020-06-14T16:00Z");
  }

}
