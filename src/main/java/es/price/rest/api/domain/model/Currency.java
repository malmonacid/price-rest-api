package es.price.rest.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {

  EUR("EUR"), USD("USD"), GBP("GBP");

  private String value;

}
