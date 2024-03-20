package es.price.rest.api.interfaces.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.price.rest.api.ApplicationTestUtils;
import es.price.rest.api.application.ports.PricePort;
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
    PriceResponseDto priceResponseDto =
        createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceResponseDto.class);

    when(priceController.getPrice(Mockito.anyString(), Mockito.anyString(), Mockito.any()))
        .thenReturn(ResponseEntity.ok(priceResponseDto));

    // act
    ResponseEntity<PriceResponseDto> response =
        priceController.getPrice(Mockito.anyString(), Mockito.anyString(), Mockito.any());

    // assert
    assertNotNull(priceResponseDto);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(priceResponseDto, response.getBody());
    assertNotNull(response.getBody());
    assertThat(output).contains("[PriceController - GET] Get /price with with params");

  }

  @Test
  void givenAnOkQueryParams_whenCallingPricesEndpointWithGivenParams_thenReturnOkResponseAndLogParams(
      CapturedOutput output) throws IOException {
    // arrange
    final String paramProductId = "54355";
    final String paramBrandId = "1";
    final OffsetDateTime paramApplicationDate =
        OffsetDateTime.of(2020, 6, 10, 10, 0, 0, 0, ZoneOffset.UTC);
    PriceResponseDto priceResponseDto =
        createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceResponseDto.class);

    when(priceController.getPrice(Mockito.anyString(), Mockito.anyString(), Mockito.any()))
        .thenReturn(ResponseEntity.ok(priceResponseDto));

    // act
    ResponseEntity<PriceResponseDto> response =
        priceController.getPrice(paramProductId, paramBrandId, paramApplicationDate);

    // assert
    assertNotNull(priceResponseDto);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertEquals(priceResponseDto, response.getBody());
    assertNotNull(response.getBody());
    assertThat(output).contains(
        "[PriceController - GET] Get /price with with params: productId: {}, brandId: {}, applicationDate: {}",
        paramProductId, paramBrandId, paramApplicationDate.toString());
  }

  @Test
  void givenAnBadQueryParams_whenCallingPricesEndpointWithGivenParams_thenReturnOkResponseAndLogParams() {
    // arrange
    final String paramProductId = null;
    final String paramBrandId = null;

    // act
    ResponseEntity<PriceResponseDto> response =
        priceController.getPrice(paramProductId, paramBrandId, Mockito.any());

    // assert
    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

}
