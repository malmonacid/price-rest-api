package es.price.rest.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Brand {

  ZARA("1"), BRAND2("2"), BRAND3("3");

  private String value;

}
