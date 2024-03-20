package es.price.rest.api.infrastructure.storage.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.price.rest.api.ApplicationTestUtils;
import es.price.rest.api.domain.model.PricesDbData;
import es.price.rest.api.infrastructure.storage.price.mapper.PricesDbDataMapper;
import es.price.rest.api.infrastructure.storage.price.mapper.PricesDbDataMapperImpl;
import es.price.rest.api.infrastructure.storage.price.model.PriceEntity;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
class PricesDbDataMapperTest extends ApplicationTestUtils {

  private PricesDbDataMapper pricesDbDataMapper;

  @BeforeEach
  void setUp() {
    pricesDbDataMapper = new PricesDbDataMapperImpl();
  }

  @Test
  void givenPriceResponse_whenTryToMapToPriceResponseDto_thenReturnAnOkPriceResponse()
      throws IOException {
    // arrange
    PriceEntity priceEntity = createObjectFromJson(TEMPLATE_PRICES_DB_ENTITY_OK, PriceEntity.class);

    // act
    PricesDbData pricesDbDataMapped = pricesDbDataMapper.toData(priceEntity);

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
}