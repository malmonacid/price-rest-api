package es.price.rest.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.mockito.InjectMocks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ApplicationTestUtils {

  protected static final String TEMPLATE_PRICE_API_RESPONSE_OK =
          "templates.json/price/response/price_api_response_ok.json";

  protected static final String TEMPLATE_PRICE_API_RESQUEST_OK =
          "templates.json/price/request/price_api_resquest_ok.json";

  protected static final String TEMPLATE_PRICES_DB_ENTITY_OK =
          "templates.json/price/entity/prices_entity.json";

  @InjectMocks
  protected ObjectMapper objectMapper;

  protected <T> T createObjectFromJson(String fileName, Class<T> type) throws IOException {
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper.readValue(loadTemplateBody(fileName), type);
  }

  protected String loadTemplateBody(final String fileName) throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
      if (inputStream == null) {
        throw new FileNotFoundException("File not found: " + fileName);
      }
      return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }
  }

}
