package es.price.rest.api.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import es.price.rest.api.application.exception.PriceDatabaseServiceException;
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
import es.price.rest.api.domain.exception.PricesDatabaseAdapterException;
import es.price.rest.api.domain.model.PriceIn;
import es.price.rest.api.domain.model.PricesData;
import es.price.rest.api.domain.ports.PricesDatabasePort;
import es.price.rest.api.domain.repository.PricesRepository;
import es.price.rest.api.domain.repository.model.Prices;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
@ContextConfiguration(classes = {PriceDataMapperImpl.class})
class PriceDatabaseServiceTest extends ApplicationTestUtils {

  private PricesDatabasePort pricesDatabasePort;

  @Mock
  private PricesRepository pricesRepository;

  @Autowired
  private PriceDataMapper pricesDbDataMapper;

  @BeforeEach
  void setUp() {
    pricesDatabasePort = new PriceDatabaseService(pricesRepository, pricesDbDataMapper);
  }

  @Test
  void givenOkRequestParams_whenCallingToFindPricesByPriceRequest_thenReturnOkData(
      CapturedOutput output) throws IOException {
    // arrange
    PriceIn priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceIn.class);
    Prices priceEntity = createObjectFromJson(TEMPLATE_PRICES_DB_ENTITY_OK, Prices.class);

    when(pricesRepository.findPriceByProductIdAndBrandIdAndDateByPriority(
        priceRequest.getProductId(), priceRequest.getBrandId(), priceRequest.getApplicationDate()))
        .thenReturn(Optional.ofNullable(priceEntity));

    // act
    PricesData pricesDataResult = pricesDatabasePort.findPricesByPriceRequest(priceRequest);

    // assert
    assertNotNull(pricesDataResult);
    Assertions.assertEquals(pricesDataResult.getProductId(), "35455", "Product is equals");
    Assertions.assertEquals(pricesDataResult.getBrandId(), "1", "Brand is the same");
    Assertions.assertEquals(pricesDataResult.getPriceList(), "2", "PriceList is the same");
    Assertions.assertEquals(pricesDataResult.getCurr(), "EUR", "Check CURR");
    Assertions.assertEquals(pricesDataResult.getPriority(), 1, "Check price");
    Assertions.assertEquals(pricesDataResult.getPrice(), 25.45, "Check priority");
    assertThat(output).contains(
        "[PriceDatabaseService - findPricesByPriceRequest()] Get price with with request");
  }

  @Test
  void givenRequestThatReturnsMoreThanOneResult_whenCallingToFindPricesByPriceRequest_thenReturnTheOneWithBestPriority(
      CapturedOutput output) throws IOException {
    // arrange
    PriceIn priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceIn.class);
    Prices priceEntity = createObjectFromJson(TEMPLATE_PRICES_DB_ENTITY_OK, Prices.class);

    when(pricesRepository.findPriceByProductIdAndBrandIdAndDateByPriority(
        priceRequest.getProductId(), priceRequest.getBrandId(), priceRequest.getApplicationDate()))
        .thenReturn(Optional.ofNullable(priceEntity));

    // act
    PricesData pricesDbDataResult = pricesDatabasePort.findPricesByPriceRequest(priceRequest);

    // assert
    assertNotNull(pricesDbDataResult);
    Assertions.assertEquals(pricesDbDataResult.getProductId(), "35455", "Product is equals");
    Assertions.assertEquals(pricesDbDataResult.getBrandId(), "1", "Brand is the same");
    Assertions.assertEquals(pricesDbDataResult.getPriceList(), "2", "PriceList is the same");
    Assertions.assertEquals(pricesDbDataResult.getCurr(), "EUR", "Check CURR");
    Assertions.assertEquals(pricesDbDataResult.getPriority(), 1, "Check priority");
    Assertions.assertEquals(pricesDbDataResult.getPrice(), 25.45, "Check price");
    assertThat(output).contains(
        "[PriceDatabaseService - findPricesByPriceRequest()] Get price with with request");
  }

  @Test
  void givenBadRequestParams_whenCallingToFindPricesByPriceRequest_thenReturnPricesDatabaseAdapterException(
      CapturedOutput output) throws IOException {
    // arrange
    PriceIn priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceIn.class);

    // assert
    assertThrows(PriceDatabaseServiceException.class,
        // act
        () -> pricesDatabasePort.findPricesByPriceRequest(priceRequest),
        "Assert PriceDatabaseServiceException is thrown when any parameter is null or malformed");
    assertThat(output).contains(
        "[ERROR PriceDatabaseService - findPricesByPriceRequest()] Get price with with request");
  }

  @Test
  void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
    List<Prices> pricesDbData = pricesRepository.findAll();
    Assertions.assertEquals(pricesDbData.size(), 0);
  }

}
