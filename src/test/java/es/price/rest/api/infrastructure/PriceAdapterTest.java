package es.price.rest.api.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.price.rest.api.ApplicationTestUtils;
import es.price.rest.api.application.ports.PricePort;
import es.price.rest.api.domain.handler.exceptions.PriceAdapterException;
import es.price.rest.api.domain.model.PriceResponse;
import es.price.rest.api.infrastructure.rest.price.PriceAdapter;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
class PriceAdapterTest extends ApplicationTestUtils {
  private PricePort pricePort;

  @BeforeEach
  void setUp() {
    pricePort = new PriceAdapter();
  }

  @Test
  void givenRequestParams_whenCallingToGetPriceAdapter_thenReturnResponseWithCorrectData(
      CapturedOutput output) throws IOException {
    // arrange
    final String paramProductId = "54355";
    final String paramBrandId = "1";
    final OffsetDateTime paramApplicationDate =
        OffsetDateTime.of(2020, 6, 10, 10, 0, 0, 0, ZoneOffset.UTC);
    PriceResponse priceResponse =
        createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceResponse.class);

    // act
    PriceResponse priceResult =
        pricePort.getPrice(paramProductId, paramBrandId, paramApplicationDate);

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
    assertThat(output).contains(
        "[PriceAdapter - getPrice()] Get price with with params: productId: {}, brandId: {}, applicationDate: {}",
        paramProductId, paramBrandId, paramApplicationDate.toString());
  }

  @Test
  void givenRequestParamsThatReturnsTwoPrices_whenCallingToGetPriceAdapter_thenReturnResponseWithBestPriority(
      CapturedOutput output) throws IOException {
    // arrange
    final String paramProductId = "54355";
    final String paramBrandId = "1";
    final OffsetDateTime paramApplicationDate =
        OffsetDateTime.of(2020, 6, 14, 10, 0, 0, 0, ZoneOffset.UTC);
    PriceResponse priceResponse =
        createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceResponse.class);

    // act
    PriceResponse priceResult =
        pricePort.getPrice(paramProductId, paramBrandId, paramApplicationDate);

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
    assertThat(output).contains(
        "[PriceAdapter - getPrice()] Get price with with params: productId: {}, brandId: {}, applicationDate: {}",
        paramProductId, paramBrandId, paramApplicationDate.toString());
  }

  @Test
  void givenRequestParamsThatDontFindAny_whenCallingToGetPriceAdapter_thenReturnPriceAdapterException(
      CapturedOutput output) {
    // arrange
    final String paramProductId = "00000";
    final String paramBrandId = "5";
    final OffsetDateTime paramApplicationDate =
        OffsetDateTime.of(2020, 6, 14, 10, 0, 0, 0, ZoneOffset.UTC);

    // assert
    assertThrows(PriceAdapterException.class,
        // act
        () -> pricePort.getPrice(paramProductId, paramBrandId, paramApplicationDate),
        "Assert PriceAdapterException is thrown when no result");
    assertThat(output).contains(
        "[PriceAdapter - getPrice()] No results found - get price with params: productId: {}, brandId: {}, applicationDate: {}",
        paramProductId, paramBrandId, paramApplicationDate.toString());
  }

  @Test
  void givenRequestParamsWithProductIdNull_whenCallingToGetPriceAdapter_thenReturnPriceAdapterException(
      CapturedOutput output) {
    // arrange
    final String paramProductId = null;
    final String paramBrandId = "1";
    final OffsetDateTime paramApplicationDate =
        OffsetDateTime.of(2020, 6, 14, 10, 0, 0, 0, ZoneOffset.UTC);

    // assert
    assertThrows(PriceAdapterException.class,
        // act
        () -> pricePort.getPrice(paramProductId, paramBrandId, paramApplicationDate),
        "Assert PriceAdapterException is thrown when any parameter is null");
    assertThat(output).contains(
        "[ERROR PriceAdapter - getPrice()] Param is null - get price with params: productId: {}, brandId: {}, applicationDate: {}",
        paramProductId, paramBrandId, paramApplicationDate.toString());
  }

  @Test
  void givenRequestParamsWithBrandIdNull_whenCallingToGetPriceAdapter_thenReturnPriceAdapterException(
      CapturedOutput output) {
    // arrange
    final String paramProductId = "54355";
    final String paramBrandId = null;
    final OffsetDateTime paramApplicationDate =
        OffsetDateTime.of(2020, 6, 14, 10, 0, 0, 0, ZoneOffset.UTC);

    // assert
    assertThrows(PriceAdapterException.class,
        // act
        () -> pricePort.getPrice(paramProductId, paramBrandId, paramApplicationDate),
        "Assert PriceAdapterException is thrown when any parameter is null");
    assertThat(output).contains(
        "[ERROR PriceAdapter - getPrice()] Param is null - get price with params: productId: {}, brandId: {}, applicationDate: {}",
        paramProductId, paramBrandId, paramApplicationDate.toString());
  }

  @Test
  void givenRequestApplicationDateNull_whenCallingToGetPriceAdapter_thenReturnPriceAdapterException(
      CapturedOutput output) {
    // arrange
    final String paramProductId = "54355";
    final String paramBrandId = "1";
    final OffsetDateTime paramApplicationDate = null;

    // assert
    assertThrows(PriceAdapterException.class,
        // act
        () -> pricePort.getPrice(paramProductId, paramBrandId, paramApplicationDate),
        "Assert PriceAdapterException is thrown when any parameter is null");
    assertThat(output).contains(
        "[ERROR PriceAdapter - getPrice()] Param is null - get price with params: productId: {}, brandId: {}, applicationDate: {}",
        paramProductId, paramBrandId, paramApplicationDate.toString());
  }

}
