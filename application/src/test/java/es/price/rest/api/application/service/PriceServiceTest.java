package es.price.rest.api.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;

import es.price.rest.api.application.exception.PriceServiceException;
import es.price.rest.api.application.mapper.PriceDataMapper;
import es.price.rest.api.application.mapper.PriceDataMapperImpl;
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
import es.price.rest.api.domain.model.PriceIn;
import es.price.rest.api.domain.model.PriceOut;
import es.price.rest.api.domain.model.PricesData;
import es.price.rest.api.domain.ports.PricePort;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
@ContextConfiguration(classes = {PriceDataMapperImpl.class})
class PriceServiceTest extends ApplicationTestUtils {
  private PricePort pricePort;
  @Mock
  private PriceDatabaseService priceDatabaseService;
  @Autowired
  private PriceDataMapper priceDataMapper;

  @BeforeEach
  void setUp() {
    pricePort = new PriceService(priceDataMapper, priceDatabaseService);
  }

  @Test
  void givenRequestParams_whenCallingToGetPriceAdapter_thenReturnResponseWithCorrectData(
      CapturedOutput output) throws IOException {
    // arrange
    PriceIn priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceIn.class);
    PriceOut priceResponse = createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceOut.class);
    PricesData pricesDbData = createObjectFromJson(TEMPLATE_PRICES_DB_ENTITY_OK, PricesData.class);

    when(priceDatabaseService.findPricesByPriceRequest(priceRequest)).thenReturn(pricesDbData);
    // act
    PriceOut priceResult = pricePort.getPrice(priceRequest);

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
    assertThat(output).contains("[PriceService - getPrice()] Get price with with request");
  }

  @Test
  void givenRequestParamsThatReturnsTwoPrices_whenCallingToGetPriceAdapter_thenReturnResponseWithBestPriority(
      CapturedOutput output) throws IOException {
    // arrange
    PriceIn priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceIn.class);
    PriceOut priceResponse = createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceOut.class);
    PricesData pricesDbData = createObjectFromJson(TEMPLATE_PRICES_DB_ENTITY_OK, PricesData.class);

    when(priceDatabaseService.findPricesByPriceRequest(priceRequest)).thenReturn(pricesDbData);

    // act
    PriceOut priceResult = pricePort.getPrice(priceRequest);

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
    assertThat(output).contains("[PriceService - getPrice()] Get price with with request");
  }

  @Test
  void givenRequestParamsThatDontFindAny_whenCallingToGetPriceAdapter_thenReturnPriceAdapterException(
      CapturedOutput output) throws IOException {
    // arrange
    PriceIn priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceIn.class);
    priceRequest.setProductId("000000");

    when(priceDatabaseService.findPricesByPriceRequest(priceRequest))
        .thenThrow(PriceServiceException.class);

    // assert
    assertThrows(PriceServiceException.class,
        // act
        () -> pricePort.getPrice(priceRequest),
        "Assert PriceServiceException is thrown when no result");
    assertThat(output).contains("[PriceService - getPrice()] Get price with with request");
  }

  @Test
  void givenRequestParamsWithProductIdNull_whenCallingToGetPriceAdapter_thenReturnPriceAdapterException(
      CapturedOutput output) throws IOException {
    // arrange
    PriceIn priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceIn.class);
    priceRequest.setProductId(null);

    when(priceDatabaseService.findPricesByPriceRequest(priceRequest))
        .thenThrow(PriceServiceException.class);

    // assert
    assertThrows(PriceServiceException.class,
        // act
        () -> pricePort.getPrice(priceRequest),
        "Assert PriceServiceException is thrown when any parameter is null");
    assertThat(output)
        .contains("[PriceService - getPrice()] Unexpected error in get price with with params");
  }

  @Test
  void givenRequestParamsWithBrandIdNull_whenCallingToGetPriceAdapter_thenReturnPriceAdapterException(
      CapturedOutput output) throws IOException {
    // arrange
    PriceIn priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceIn.class);
    priceRequest.setBrandId(null);

    when(priceDatabaseService.findPricesByPriceRequest(priceRequest))
        .thenThrow(PriceServiceException.class);

    // assert
    assertThrows(PriceServiceException.class,
        // act
        () -> pricePort.getPrice(priceRequest),
        "Assert PriceServiceException is thrown when any parameter is null");
    assertThat(output)
        .contains("[PriceService - getPrice()] Unexpected error in get price with with params");
  }

  @Test
  void givenRequestApplicationDateNull_whenCallingToGetPriceAdapter_thenReturnPriceAdapterException(
      CapturedOutput output) throws IOException {
    // arrange
    PriceIn priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceIn.class);
    priceRequest.setApplicationDate(null);

    when(priceDatabaseService.findPricesByPriceRequest(priceRequest))
        .thenThrow(PriceServiceException.class);

    // assert
    assertThrows(PriceServiceException.class,
        // act
        () -> pricePort.getPrice(priceRequest),
        "Assert PriceServiceException is thrown when any parameter is null");
    assertThat(output)
        .contains("[PriceService - getPrice()] Unexpected error in get price with with params");
  }

}
