package es.price.rest.api.application.find.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.price.rest.api.ApplicationTestUtils;
import es.price.rest.api.domain.model.Price;
import es.price.rest.api.domain.model.PriceOut;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
class PriceDataMapperTest extends ApplicationTestUtils {

  private PriceDataMapper priceDataMapper;

  @BeforeEach
  void setUp() {
    priceDataMapper = new PriceDataMapperImpl();
  }

  @Test
  void givenPriceResponse_whenTryToMapToModel_thenReturnAnOkPriceResponse() throws IOException {
    // arrange
    Price price = createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, Price.class);

    // act
    PriceOut resMapped = priceDataMapper.toModel(price);

    // assert
    assertNotNull(resMapped);
    assertEquals(price.getProductId(), resMapped.getProductId(), "Assert product id equals");
    assertEquals(price.getBrandId(), resMapped.getBrandId(), "Assert brand id is the same");
    assertEquals(price.getPriceList(), resMapped.getPriceList(), "Assert price list is the same");
    assertEquals(price.getStartDate(), resMapped.getStartDate(), "Assert start date equals");
    assertEquals(price.getEndDate(), resMapped.getEndDate(), "Assert end date equals");
  }

}
