package es.price.rest.api.interfaces.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.price.rest.api.ApplicationTestUtils;
import es.price.rest.api.application.ports.PricePort;
import es.price.rest.api.domain.model.PriceRequest;
import es.price.rest.api.domain.model.PriceResponse;
import es.price.rest.api.infrastructure.rest.price.dto.PriceResponseDto;
import es.price.rest.api.infrastructure.rest.price.mapper.PriceResponseDtoMapper;
import es.price.rest.api.infrastructure.rest.price.mapper.PriceResponseDtoMapperImpl;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
@ContextConfiguration(classes = {PriceResponseDtoMapperImpl.class})
public class PriceControllerTest extends ApplicationTestUtils {

  private PriceController priceController;
  @Mock
  private PricePort pricePort;

  @Autowired
  PriceResponseDtoMapper priceResponseDtoMapper;

  @BeforeEach
  void setUp() {
    priceController = new PriceController(pricePort, priceResponseDtoMapper);
  }

  @Test
  void givenAnOkQueryParams_whenCallingPricesEndpointWithCorrectParams_thenReturnOkResponse(
      CapturedOutput output) throws IOException {
    // arrange
    PriceRequest priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceRequest.class);
    PriceResponse priceResponse =
        createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceResponse.class);

    when(pricePort.getPrice(priceRequest)).thenReturn(priceResponse);

    // act
    ResponseEntity<PriceResponseDto> response = priceController.getPrice(
        priceRequest.getProductId(), priceRequest.getBrandId(), priceRequest.getApplicationDate());

    // assert
    assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    assertThat(output).contains("[PriceController - /price] Get price with params");
  }

  @Test
  void givenAnOkQueryParams_whenCallingPricesEndpointWithGivenParams_thenReturnOkResponseAndLogParams(
      CapturedOutput output) throws IOException {
    // arrange
    PriceRequest priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceRequest.class);
    PriceResponse priceResponse =
        createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceResponse.class);

    when(pricePort.getPrice(priceRequest)).thenReturn(priceResponse);

    // act
    ResponseEntity<PriceResponseDto> response = priceController.getPrice(
        priceRequest.getProductId(), priceRequest.getBrandId(), priceRequest.getApplicationDate());

    // assert
    assertNotNull(response);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    assertThat(output).contains(
        "[PriceController - /price] Get price with params: productId: 35455, brandId: 1, applicationDate: 2020-06-14T16:00Z");
  }

}
