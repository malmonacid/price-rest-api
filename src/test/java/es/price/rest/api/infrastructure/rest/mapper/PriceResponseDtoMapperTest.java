package es.price.rest.api.infrastructure.rest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.price.rest.api.ApplicationTestUtils;
import es.price.rest.api.domain.model.PriceResponse;
import es.price.rest.api.infrastructure.rest.price.dto.PriceResponseDto;
import es.price.rest.api.infrastructure.rest.price.mapper.PriceResponseDtoMapper;
import es.price.rest.api.infrastructure.rest.price.mapper.PriceResponseDtoMapperImpl;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
class PriceResponseDtoMapperTest extends ApplicationTestUtils {

  private PriceResponseDtoMapper priceResponseDtoMapper;

  @BeforeEach
  void setUp() {
    priceResponseDtoMapper = new PriceResponseDtoMapperImpl();
  }

  @Test
  void givenPriceResponse_whenTryToMapToPriceResponseDto_thenReturnAnOkPriceResponse()
      throws IOException {
    // arrange
    PriceResponse priceResponse =
        createObjectFromJson(TEMPLATE_PRICE_API_RESPONSE_OK, PriceResponse.class);

    // act
    PriceResponseDto resMapped = priceResponseDtoMapper.toDto(priceResponse);

    // assert
    assertNotNull(resMapped);
    assertEquals(priceResponse.getProductId(), resMapped.getProductId(),
        "Assert product id equals");
    assertEquals(priceResponse.getBrandId(), resMapped.getBrandId(), "Assert brand id is the same");
    assertEquals(priceResponse.getPriceList(), resMapped.getPriceList(),
        "Assert price list is the same");
    assertEquals(priceResponse.getStartDate(), resMapped.getStartDate(),
        "Assert start date equals");
    assertEquals(priceResponse.getEndDate(), resMapped.getEndDate(), "Assert end date equals");
  }
}
