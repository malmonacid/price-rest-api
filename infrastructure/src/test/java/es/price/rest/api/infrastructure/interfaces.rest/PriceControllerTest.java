package es.price.rest.api.infrastructure.interfaces.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;

import es.price.rest.api.ApplicationTestUtils;
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

import es.price.rest.api.domain.model.PriceIn;
import es.price.rest.api.domain.model.PriceOut;
import es.price.rest.api.domain.ports.PricePort;
import es.price.rest.api.infrastructure.rest.mapper.PriceResponseDtoMapper;
import es.price.rest.api.infrastructure.rest.mapper.PriceResponseDtoMapperImpl;

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
    PriceIn priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceIn.class);
    PriceOut priceResponse = createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceOut.class);

    when(pricePort.getPrice(priceRequest)).thenReturn(priceResponse);

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
    PriceIn priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceIn.class);
    PriceOut priceResponse = createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceOut.class);

    when(pricePort.getPrice(priceRequest)).thenReturn(priceResponse);

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
