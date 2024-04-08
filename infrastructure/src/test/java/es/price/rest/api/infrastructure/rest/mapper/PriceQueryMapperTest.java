package es.price.rest.api.infrastructure.rest.mapper;

import es.price.rest.api.ApplicationTestUtils;
import es.price.rest.api.domain.model.PriceQuery;
import es.price.rest.api.infrastructure.rest.model.PriceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
class PriceQueryMapperTest extends ApplicationTestUtils {

    private PriceQueryMapper priceQueryMapper;

    @BeforeEach
    void setUp() {
        priceQueryMapper = new PriceQueryMapperImpl();
    }

    @Test
    void givenPriceResponse_whenTryToMapToPriceResponseDto_thenReturnAnOkPriceResponse()
            throws IOException {
        // arrange
        PriceDto priceDto = createObjectFromJson(TEMPLATE_PRICE_API_RESQUEST_OK, PriceDto.class);

        // act
        PriceQuery resMapped = priceQueryMapper.toQuery(priceDto);

        // assert
        assertNotNull(resMapped);
        assertEquals(priceDto.getProductId(), resMapped.getProductId(),
                "Assert product id equals");
        assertEquals(priceDto.getBrandId(), resMapped.getBrandId(), "Assert brand id is the same");
        assertEquals(priceDto.getApplicationDate(), resMapped.getApplicationDate(), "Assert application date equals");
    }
}
