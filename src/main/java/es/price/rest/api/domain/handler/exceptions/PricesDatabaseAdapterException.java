package es.price.rest.api.domain.handler.exceptions;

public class PricesDatabaseAdapterException extends RuntimeException {
  public PricesDatabaseAdapterException(Throwable cause) {
    super(cause);
  }

  public PricesDatabaseAdapterException(String msg) {
    super(msg);
  }

}
