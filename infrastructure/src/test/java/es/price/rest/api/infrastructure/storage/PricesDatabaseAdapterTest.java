package es.price.rest.api.infrastructure.storage;

import es.price.rest.api.ApplicationTestUtils;
import es.price.rest.api.domain.exception.PricesDatabaseAdapterException;
import es.price.rest.api.domain.model.Price;
import es.price.rest.api.domain.model.PriceQuery;
import es.price.rest.api.domain.ports.PricesDatabasePort;
import es.price.rest.api.domain.repository.PricesRepository;
import es.price.rest.api.domain.repository.model.Prices;
import es.price.rest.api.infrastructure.storage.mapper.PricesDbDataMapper;
import es.price.rest.api.infrastructure.storage.mapper.PricesDbDataMapperImpl;
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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
@ContextConfiguration(classes = {PricesDbDataMapperImpl.class})
class PricesDatabaseAdapterTest extends ApplicationTestUtils {

  private PricesDatabasePort pricesDatabasePort;

  @Mock
  private PricesRepository pricesRepository;

  @Autowired
  private PricesDbDataMapper pricesDbDataMapper;

  @BeforeEach
  void setUp() {
    pricesDatabasePort = new PricesDatabaseAdapter(pricesRepository, pricesDbDataMapper);
  }

  @Test
  void givenOkRequestParams_whenCallingToFindPricesByPriceRequest_thenReturnOkData(
      CapturedOutput output) throws IOException {
    // arrange
    PriceQuery priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceQuery.class);
    Prices priceEntity = createObjectFromJson(
        TEMPLATE_PRICES_DB_ENTITY_OK, Prices.class);

    when(pricesRepository.findPriceByProductIdAndBrandIdAndDateByPriority(
        priceRequest.getProductId(), priceRequest.getBrandId(), priceRequest.getApplicationDate()))
        .thenReturn(Optional.ofNullable(priceEntity));

    // act
    Price priceResult = pricesDatabasePort.findPricesByPriceRequest(priceRequest);

    // assert
    assertNotNull(priceResult);
    Assertions.assertEquals(priceResult.getProductId(), "35455", "Product is equals");
    Assertions.assertEquals(priceResult.getBrandId(), "1", "Brand is the same");
    Assertions.assertEquals(priceResult.getPriceList(), "2", "PriceList is the same");
    Assertions.assertEquals(priceResult.getCurr(), "EUR", "Check CURR");
    Assertions.assertEquals(priceResult.getPriority(), 1, "Check price");
    Assertions.assertEquals(priceResult.getPrice(), 25.45, "Check priority");
    assertThat(output).contains(
        "[PricesDatabaseAdapter - findPricesByPriceRequest()] Get price with with request");
  }

  @Test
  void givenRequestThatReturnsMoreThanOneResult_whenCallingToFindPricesByPriceRequest_thenReturnTheOneWithBestPriority(
      CapturedOutput output) throws IOException {
    // arrange
    PriceQuery priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceQuery.class);
    Prices priceEntity = createObjectFromJson(
        TEMPLATE_PRICES_DB_ENTITY_OK, Prices.class);

    when(pricesRepository.findPriceByProductIdAndBrandIdAndDateByPriority(
        priceRequest.getProductId(), priceRequest.getBrandId(), priceRequest.getApplicationDate()))
        .thenReturn(Optional.ofNullable(priceEntity));

    // act
    Price pricesDbDataResult = pricesDatabasePort.findPricesByPriceRequest(priceRequest);

    // assert
    assertNotNull(pricesDbDataResult);
    Assertions.assertEquals(pricesDbDataResult.getProductId(), "35455", "Product is equals");
    Assertions.assertEquals(pricesDbDataResult.getBrandId(), "1", "Brand is the same");
    Assertions.assertEquals(pricesDbDataResult.getPriceList(), "2", "PriceList is the same");
    Assertions.assertEquals(pricesDbDataResult.getCurr(), "EUR", "Check CURR");
    Assertions.assertEquals(pricesDbDataResult.getPriority(), 1, "Check priority");
    Assertions.assertEquals(pricesDbDataResult.getPrice(), 25.45, "Check price");
    assertThat(output).contains(
        "[PricesDatabaseAdapter - findPricesByPriceRequest()] Get price with with request");
  }

  @Test
  void givenBadRequestParams_whenCallingToFindPricesByPriceRequest_thenReturnPricesDatabaseAdapterException(
      CapturedOutput output) throws IOException {
    // arrange
    PriceQuery priceRequest = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceQuery.class);

    // assert
    assertThrows(PricesDatabaseAdapterException.class,
        // act
        () -> pricesDatabasePort.findPricesByPriceRequest(priceRequest),
        "Assert PriceAdapterException is thrown when any parameter is null or malformed");
    assertThat(output).contains(
        "[ERROR PricesDatabaseAdapter - findPricesByPriceRequest()] Get price with with request");
  }

  @Test
  void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
    List<Prices> pricesDbData = pricesRepository.findAll();
    Assertions.assertEquals(pricesDbData.size(), 0);
  }

}
