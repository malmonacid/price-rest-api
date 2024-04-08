package es.price.rest.api.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import es.price.rest.api.ApplicationTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.price.rest.api.domain.model.PriceOut;
import es.price.rest.api.domain.model.PricesData;
import es.price.rest.api.domain.repository.model.Prices;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
class PriceDataMapperTest extends ApplicationTestUtils {

  private PriceDataMapper priceDataMapper;

  @BeforeEach
  void setUp() {
    priceDataMapper = new PriceDataMapperImpl();
  }

  @Test
  void givenPriceResponse_whenTryToMapToPriceResponseDto_thenReturnAnEqualPriceResponse()
      throws IOException {
    // arrange
    Prices priceEntity = createObjectFromJson(TEMPLATE_PRICES_DB_ENTITY_OK, Prices.class);

    // act
    PricesData pricesDbDataMapped = priceDataMapper.toData(priceEntity);

    // assert
    assertNotNull(pricesDbDataMapped);
    assertEquals(priceEntity.getProductId(), pricesDbDataMapped.getProductId(),
        "Assert product id equals");
    assertEquals(priceEntity.getPrice(), pricesDbDataMapped.getPrice(), "Assert price id equals");
    assertEquals(priceEntity.getCurr(), pricesDbDataMapped.getCurr(), "Assert CURR id equals");
    assertEquals(priceEntity.getPriority(), pricesDbDataMapped.getPriority(),
        "Assert Priority is equals");
    assertEquals(priceEntity.getBrandId(), pricesDbDataMapped.getBrandId(),
        "Assert brand id is the same");
    assertEquals(priceEntity.getPriceList(), pricesDbDataMapped.getPriceList(),
        "Assert price list is the same");
    assertEquals(priceEntity.getStartDate(), pricesDbDataMapped.getStartDate(),
        "Assert start date equals");
    assertEquals(priceEntity.getEndDate(), pricesDbDataMapped.getEndDate(),
        "Assert end date equals");
  }

  @Test
  void givenPriceResponse_whenTryToMapToModel_thenReturnAnOkPriceResponse()
      throws IOException {
    // arrange
    PricesData pricesData = createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PricesData.class);

    // act
    PriceOut resMapped = priceDataMapper.toModel(pricesData);

    // assert
    assertNotNull(resMapped);
    assertEquals(pricesData.getProductId(), resMapped.getProductId(),
        "Assert product id equals");
    assertEquals(pricesData.getBrandId(), resMapped.getBrandId(), "Assert brand id is the same");
    assertEquals(pricesData.getPriceList(), resMapped.getPriceList(),
        "Assert price list is the same");
    assertEquals(pricesData.getStartDate(), resMapped.getStartDate(),
        "Assert start date equals");
    assertEquals(pricesData.getEndDate(), resMapped.getEndDate(), "Assert end date equals");
  }

}
