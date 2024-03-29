package es.price.rest.api.infrastructure.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.price.rest.api.ApplicationTestUtils;
import es.price.rest.api.application.ports.PricePort;
import es.price.rest.api.domain.handler.exceptions.PriceAdapterException;
import es.price.rest.api.domain.model.PriceRequest;
import es.price.rest.api.domain.model.PriceResponse;
import es.price.rest.api.domain.model.PricesDbData;
import es.price.rest.api.infrastructure.rest.price.PriceAdapter;
import es.price.rest.api.infrastructure.rest.price.mapper.PriceResponseDbMapper;
import es.price.rest.api.infrastructure.rest.price.mapper.PriceResponseDbMapperImpl;
import es.price.rest.api.infrastructure.storage.price.PricesDatabaseAdapter;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
@ContextConfiguration(classes = {PriceResponseDbMapperImpl.class})
class PriceAdapterTest extends ApplicationTestUtils {
  private PricePort pricePort;
  @Mock
  private PricesDatabaseAdapter pricesDatabaseAdapter;
  @Autowired
  private PriceResponseDbMapper priceResponseDbMapper;

  @BeforeEach
  void setUp() {
    pricePort = new PriceAdapter(pricesDatabaseAdapter, priceResponseDbMapper);
  }

  @Test
  void givenRequestParams_whenCallingToGetPriceAdapter_thenReturnResponseWithCorrectData(
      CapturedOutput output) throws IOException {
    // arrange
    PriceRequest priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceRequest.class);
    PriceResponse priceResponse =
        createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceResponse.class);
    PricesDbData pricesDbData =
        createObjectFromJson(TEMPLATE_PRICES_DB_ENTITY_OK, PricesDbData.class);

    when(pricesDatabaseAdapter.findPricesByPriceRequest(priceRequest)).thenReturn(pricesDbData);
    // act
    PriceResponse priceResult = pricePort.getPrice(priceRequest);

    // assert
    assertNotNull(priceResult);
    Assertions.assertEquals(priceResult.getProductId(), priceResponse.getProductId(),
        "Product is equals");
    Assertions.assertEquals(priceResult.getBrandId(), priceResponse.getBrandId(),
        "Brand is the same");
    Assertions.assertEquals(priceResult.getPriceList(), priceResponse.getPriceList(),
        "PriceList is the same");
    Assertions.assertEquals(priceResult.getStartDate(), priceResponse.getStartDate(),
        "Check start date");
    Assertions.assertEquals(priceResult.getEndDate(), priceResponse.getEndDate(), "Check end date");
    assertThat(output).contains("[PriceAdapter - getPrice()] Get price with with request");
  }

  @Test
  void givenRequestParamsThatReturnsTwoPrices_whenCallingToGetPriceAdapter_thenReturnResponseWithBestPriority(
      CapturedOutput output) throws IOException {
    // arrange
    PriceRequest priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceRequest.class);
    PriceResponse priceResponse =
        createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceResponse.class);
    PricesDbData pricesDbData =
        createObjectFromJson(TEMPLATE_PRICES_DB_ENTITY_OK, PricesDbData.class);

    when(pricesDatabaseAdapter.findPricesByPriceRequest(priceRequest)).thenReturn(pricesDbData);

    // act
    PriceResponse priceResult = pricePort.getPrice(priceRequest);

    // assert
    assertNotNull(priceResult);
    Assertions.assertEquals(priceResult.getProductId(), priceResponse.getProductId(),
        "Product is equals");
    Assertions.assertEquals(priceResult.getBrandId(), priceResponse.getBrandId(),
        "Brand is the same");
    Assertions.assertEquals(priceResult.getPriceList(), priceResponse.getPriceList(),
        "PriceList is the same");
    Assertions.assertEquals(priceResult.getStartDate(), priceResponse.getStartDate(),
        "Check start date");
    Assertions.assertEquals(priceResult.getEndDate(), priceResponse.getEndDate(), "Check end date");
    assertThat(output).contains("[PriceAdapter - getPrice()] Get price with with request");
  }

  @Test
  void givenRequestParamsThatDontFindAny_whenCallingToGetPriceAdapter_thenReturnPriceAdapterException(
      CapturedOutput output) throws IOException {
    // arrange
    PriceRequest priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceRequest.class);
    priceRequest.setProductId("000000");

    when(pricesDatabaseAdapter.findPricesByPriceRequest(priceRequest))
        .thenThrow(PriceAdapterException.class);

    // assert
    assertThrows(PriceAdapterException.class,
        // act
        () -> pricePort.getPrice(priceRequest),
        "Assert PriceAdapterException is thrown when no result");
    assertThat(output).contains("[PriceAdapter - getPrice()] Get price with with request");
  }

  @Test
  void givenRequestParamsWithProductIdNull_whenCallingToGetPriceAdapter_thenReturnPriceAdapterException(
      CapturedOutput output) throws IOException {
    // arrange
    PriceRequest priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceRequest.class);
    priceRequest.setProductId(null);

    when(pricesDatabaseAdapter.findPricesByPriceRequest(priceRequest))
        .thenThrow(PriceAdapterException.class);

    // assert
    assertThrows(PriceAdapterException.class,
        // act
        () -> pricePort.getPrice(priceRequest),
        "Assert PriceAdapterException is thrown when any parameter is null");
    assertThat(output)
        .contains("[PriceAdapter - getPrice()] Unexpected error in get price with with params");
  }

  @Test
  void givenRequestParamsWithBrandIdNull_whenCallingToGetPriceAdapter_thenReturnPriceAdapterException(
      CapturedOutput output) throws IOException {
    // arrange
    PriceRequest priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceRequest.class);
    priceRequest.setBrandId(null);

    when(pricesDatabaseAdapter.findPricesByPriceRequest(priceRequest))
        .thenThrow(PriceAdapterException.class);

    // assert
    assertThrows(PriceAdapterException.class,
        // act
        () -> pricePort.getPrice(priceRequest),
        "Assert PriceAdapterException is thrown when any parameter is null");
    assertThat(output)
        .contains("[PriceAdapter - getPrice()] Unexpected error in get price with with params");
  }

  @Test
  void givenRequestApplicationDateNull_whenCallingToGetPriceAdapter_thenReturnPriceAdapterException(
      CapturedOutput output) throws IOException {
    // arrange
    PriceRequest priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceRequest.class);
    priceRequest.setApplicationDate(null);

    when(pricesDatabaseAdapter.findPricesByPriceRequest(priceRequest))
        .thenThrow(PriceAdapterException.class);

    // assert
    assertThrows(PriceAdapterException.class,
        // act
        () -> pricePort.getPrice(priceRequest),
        "Assert PriceAdapterException is thrown when any parameter is null");
    assertThat(output)
        .contains("[PriceAdapter - getPrice()] Unexpected error in get price with with params");
  }

}
