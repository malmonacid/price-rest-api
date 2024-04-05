package es.price.rest.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.ServletContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Application.class})
@WebAppConfiguration
public class PriceControllerIntegrationTest {
  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @Test
  public void givenWac_whenServletContext_thenItProvidesPriceController() {
    ServletContext servletContext = webApplicationContext.getServletContext();

    Assertions.assertNotNull(servletContext);
    Assertions.assertInstanceOf(MockServletContext.class, servletContext);
    Assertions.assertNotNull(webApplicationContext.getBean("priceController"));
  }

  @Test
  @Sql("/data.sql")
  void givenPricesUriWithQueryParameter_whenMockMVC_thenReturnTest1ResponseOK() throws Exception {
    // arrange
    MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
    paramMap.add("applicationDate", "2020-06-14T10:00:00Z");
    paramMap.add("productId", "35455");
    paramMap.add("brandId", "1");

    // assert
    this.mockMvc.perform(get("/prices").params(paramMap)).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value("35455"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value("1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value("1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-06-14T00:00:00+02:00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-12-31T23:59:59+01:00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(35.5));
  }

  @Test
  @Sql("/data.sql")
  void givenPricesUriWithQueryParameter_whenMockMVC_thenReturnTest2ResponseOK() throws Exception {
    // arrange
    MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
    paramMap.add("applicationDate", "2020-06-14T16:00:00Z");
    paramMap.add("productId", "35455");
    paramMap.add("brandId", "1");

    // assert
    this.mockMvc.perform(get("/prices").params(paramMap)).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value("35455"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value("1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value("2"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-06-14T15:00:00+02:00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-06-14T18:30:00+02:00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(25.45));
  }

  @Test
  @Sql("/data.sql")
  void givenPricesUriWithQueryParameter_whenMockMVC_thenReturnTest3ResponseOK() throws Exception {
    // arrange
    MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
    paramMap.add("applicationDate", "2020-06-14T21:00:00Z");
    paramMap.add("productId", "35455");
    paramMap.add("brandId", "1");

    // assert
    this.mockMvc.perform(get("/prices").params(paramMap)).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value("35455"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value("1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value("1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-06-14T00:00:00+02:00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-12-31T23:59:59+01:00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(35.5));
  }

  @Test
  @Sql("/data.sql")
  void givenPricesUriWithQueryParameter_whenMockMVC_thenReturnTest4ResponseOK() throws Exception {
    // arrange
    MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
    paramMap.add("applicationDate", "2020-06-15T00:00:00Z");
    paramMap.add("productId", "35455");
    paramMap.add("brandId", "1");

    // assert
    this.mockMvc.perform(get("/prices").params(paramMap)).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value("35455"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value("1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value("3"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-06-15T00:00:00+02:00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-06-15T11:00:00+02:00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(30.5));
  }

  @Test
  @Sql("/data.sql")
  void givenPricesUriWithQueryParameter_whenMockMVC_thenReturnTest5ResponseOK() throws Exception {
    // arrange
    MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
    paramMap.add("applicationDate", "2020-06-16T21:00:00Z");
    paramMap.add("productId", "35455");
    paramMap.add("brandId", "1");

    // assert
    this.mockMvc.perform(get("/prices").params(paramMap)).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value("35455"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value("1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.priceList").value("4"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-06-15T16:00:00+02:00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-12-31T23:59:59+01:00"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(38.95));
  }

}
