package es.price.rest.api.infrastructure.storage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

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
import es.price.rest.api.application.ports.PricesDatabasePort;
import es.price.rest.api.domain.handler.exceptions.PriceAdapterException;
import es.price.rest.api.domain.model.PriceRequest;
import es.price.rest.api.domain.model.PricesDbData;
import es.price.rest.api.infrastructure.storage.price.PricesDatabaseAdapter;
import es.price.rest.api.infrastructure.storage.price.mapper.PricesDbDataMapper;
import es.price.rest.api.infrastructure.storage.price.mapper.PricesDbDataMapperImpl;
import es.price.rest.api.infrastructure.storage.price.model.PriceEntity;
import es.price.rest.api.infrastructure.storage.price.repository.PricesRepository;

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
    PriceRequest priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceRequest.class);
    PricesDbData pricesEntityResponse =
        createObjectFromJson(TEMPLATE_PRICES_DB_ENTITY_OK, PricesDbData.class);

    when(pricesDatabasePort.findPricesByPriceRequest(priceRequest))
        .thenReturn(pricesEntityResponse);

    // act
    PricesDbData pricesDbDataResult = pricesDatabasePort.findPricesByPriceRequest(priceRequest);

    // assert
    assertNotNull(pricesDbDataResult);
    Assertions.assertEquals(pricesDbDataResult.getProductId(), "35455", "Product is equals");
    Assertions.assertEquals(pricesDbDataResult.getBrandId(), "1", "Brand is the same");
    Assertions.assertEquals(pricesDbDataResult.getPriceList(), "2", "PriceList is the same");
    Assertions.assertEquals(pricesDbDataResult.getCurr(), "EUR", "Check CURR");
    Assertions.assertEquals(pricesDbDataResult.getPriority(), 1, "Check price");
    Assertions.assertEquals(pricesDbDataResult.getPrice(), 25.45, "Check priority");
    assertThat(output).contains(
        "[PricesDatabaseAdapter - findPricesByPriceRequest()] Get price with with request");
  }

  @Test
  void givenRequestThatReturnsMoreThanOneResult_whenCallingToFindPricesByPriceRequest_thenReturnTheOneWithBestPriority(
      CapturedOutput output) throws IOException {
    // arrange
    PriceRequest priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceRequest.class);
    PricesDbData pricesEntityResponse =
        createObjectFromJson(TEMPLATE_PRICES_DB_ENTITY_OK, PricesDbData.class);

    when(pricesDatabasePort.findPricesByPriceRequest(priceRequest))
        .thenReturn(pricesEntityResponse);

    // act
    PricesDbData pricesDbDataResult = pricesDatabasePort.findPricesByPriceRequest(priceRequest);

    // assert
    assertNotNull(pricesDbDataResult);
    Assertions.assertEquals(pricesDbDataResult.getProductId(), "35455", "Product is equals");
    Assertions.assertEquals(pricesDbDataResult.getBrandId(), "", "Brand is the same");
    Assertions.assertEquals(pricesDbDataResult.getPriceList(), "", "PriceList is the same");
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
    PriceRequest priceRequest =
        createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceRequest.class);

    // assert
    assertThrows(PriceAdapterException.class,
        // act
        () -> pricesDatabasePort.findPricesByPriceRequest(priceRequest),
        "Assert PriceAdapterException is thrown when any parameter is null or malformed");
    assertThat(output).contains(
        "[ERROR PricesDatabaseAdapter - findPricesByPriceRequest()] Get price with with request");
  }

  @Test
  void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
    List<PriceEntity> pricesDbData = pricesRepository.findAll();
    Assertions.assertEquals(pricesDbData.size(), 0);
  }

}
